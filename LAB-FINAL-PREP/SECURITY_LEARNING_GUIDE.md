# Complete Step-by-Step Spring Security & JWT Learning Guide

This guide breaks down every class and concept in the **`com.example.labfinal.security`** package, arranged in the exact logical order in which Spring Security works.

---

## The Architecture at a Glance

Spring Security operates as a **FilterChain** (a series of servlet filters). Every incoming HTTP request must pass through this chain before reaching your Controllers.

```
                      [ Incoming HTTP Request ]
                                  |
                                  v
                   +-----------------------------+
                   |   JwtAuthenticationFilter   |  <-- Checks for "Authorization: Bearer <token>"
                   +-----------------------------+
                                  |
                                  v
                   +-----------------------------+
                   | UsernamePasswordAuthFilter  |  <-- Handles Form Login (/login POST)
                   +-----------------------------+
                                  |
                                  v
                   +-----------------------------+
                   |   FilterSecurityInterceptor |  <-- Enforces role rules (ADMIN, USER)
                   +-----------------------------+
                                  |
                                  v
                   [ Controller / API / View ]
```

---

## Step 1: The Password Hashing Foundation
### File: [PasswordEncoderConfig.java](file:///c:/Siam/Git%20Projects/Advance-Java-Practice/LAB-FINAL-PREP/src/main/java/com/example/labfinal/config/PasswordEncoderConfig.java)

Before authenticating any user, you must never store passwords in plain text. Spring provides `PasswordEncoder`.

```java
package com.example.labfinal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

### Why is this class isolated?
- If you declare `@Bean public PasswordEncoder passwordEncoder()` inside `SecurityConfig`, a **Circular Dependency** can occur:
  `SecurityConfig` -> `CustomAuthenticationProvider` -> `PasswordEncoder` -> `SecurityConfig`.
- Putting it in `PasswordEncoderConfig` breaks the cycle cleanly following the **Single Responsibility Principle**.

### What does BCrypt do?
- Uses an adaptive salt and key derivation function.
- Every call to `passwordEncoder.encode("admin123")` generates a different hash string because a random salt is included in the hash.
- The `passwordEncoder.matches(rawPassword, encodedPassword)` extracts the salt from the hash to verify equality.

---

## Step 2: Fetching User Data from Database
### File: [UserDetailsServiceImpl.java](file:///c:/Siam/Git%20Projects/Advance-Java-Practice/LAB-FINAL-PREP/src/main/java/com/example/labfinal/security/UserDetailsServiceImpl.java)

Spring Security needs a standard way to query users from your database. It uses the `UserDetailsService` interface.

```java
package com.example.labfinal.security;

import com.example.labfinal.entity.AppUser;
import com.example.labfinal.repository.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        List<GrantedAuthority> authorities = appUser.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }
}
```

### Key Concepts:
1. **`UserDetailsService` Interface:** Has one single method: `loadUserByUsername(String username)`.
2. **`UserRepository.findByUsername(username)`:** Queries the MySQL `app_users` table.
3. **`GrantedAuthority` / `SimpleGrantedAuthority`:** Spring Security does not use plain strings for roles. It converts role strings (like `"ROLE_ADMIN"`, `"ROLE_USER"`) into `GrantedAuthority` objects.
4. **`org.springframework.security.core.userdetails.User`:** Spring Security's built-in implementation of `UserDetails` containing:
   - Username
   - Encrypted Password
   - Collection of Authorities (Roles)

---

## Step 3: Validating Credentials
### File: [CustomAuthenticationProvider.java](file:///c:/Siam/Git%20Projects/Advance-Java-Practice/LAB-FINAL-PREP/src/main/java/com/example/labfinal/security/CustomAuthenticationProvider.java)

`AuthenticationProvider` is where the actual authentication logic takes place (comparing username and password).

```java
package com.example.labfinal.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
```

### How the `authenticate(...)` method works:
1. **Extracts Input:** `authentication.getName()` (entered username) and `authentication.getCredentials()` (entered raw password).
2. **Loads Account:** Calls `userDetailsService.loadUserByUsername(username)`.
3. **Matches Passwords:** `passwordEncoder.matches(rawInput, hashedPassword)`.
   - If false: throws `BadCredentialsException("Invalid username or password")`.
4. **Returns Fully Authenticated Token:** Returns a new `UsernamePasswordAuthenticationToken` containing the `userDetails`, credentials, and `authorities`.
5. **`supports(Class<?> authentication)`:** Tells Spring Security that this provider supports `UsernamePasswordAuthenticationToken` requests.

---

## Step 4: The JWT Engine (Token Generation & Validation)
### File: [JwtUtils.java](file:///c:/Siam/Git%20Projects/Advance-Java-Practice/LAB-FINAL-PREP/src/main/java/com/example/labfinal/security/jwt/JwtUtils.java)

JSON Web Tokens allow stateless communication without storing user sessions on the server.

```java
package com.example.labfinal.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${jwt.secret:404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970}")
    private String jwtSecret;

    @Value("${jwt.expiration.ms:86400000}")
    private long jwtExpirationMs;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
```

### The Anatomy of a JWT:
A token has 3 parts separated by dots (`header.payload.signature`):
1. **Header:** Algorithm used (`HS256`).
2. **Payload (Claims):**
   - `sub`: Username / Subject.
   - `iat`: Issued at timestamp.
   - `exp`: Expiration timestamp (e.g. 24 hours later).
   - `roles`: `["ROLE_ADMIN", "ROLE_USER"]`.
3. **Signature:** Cryptographic signature calculated with the secret key (`HMAC-SHA256`).

### Core Methods:
- `generateToken(UserDetails userDetails)`: Builds and signs the token string.
- `extractUsername(token)`: Reads the `sub` claim.
- `isTokenValid(token, userDetails)`: Checks that the username matches and expiration date is in the future.

---

## Step 5: The Per-Request JWT Interceptor Filter
### File: [JwtAuthenticationFilter.java](file:///c:/Siam/Git%20Projects/Advance-Java-Practice/LAB-FINAL-PREP/src/main/java/com/example/labfinal/security/jwt/JwtAuthenticationFilter.java)

This filter intercepts every incoming HTTP request to check if it has a JWT in the `Authorization` header.

```java
package com.example.labfinal.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String username;
        try {
            username = jwtUtils.extractUsername(jwt);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtils.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
```

### Step-by-Step Execution in `doFilterInternal`:
1. **Reads Header:** Looks for `Authorization: Bearer <token>`.
2. **If missing:** It immediately passes the request along with `filterChain.doFilter(request, response)`. (This allows standard form login users or public assets to pass).
3. **If present:** Trims `"Bearer "` (index 7 onwards) to extract the pure token.
4. **Extracts Username:** Parses token payload.
5. **Checks SecurityContext:** If user is not yet authenticated in the current thread (`SecurityContextHolder.getContext().getAuthentication() == null`).
6. **Validates Token:** Calls `jwtUtils.isTokenValid(jwt, userDetails)`.
7. **Sets Authentication:** Creates `UsernamePasswordAuthenticationToken` and saves it into `SecurityContextHolder`. From this point forward, Spring Security considers the user logged in for this request!

---

## Step 6: Master Security Configuration
### File: [SecurityConfig.java](file:///c:/Siam/Git%20Projects/Advance-Java-Practice/LAB-FINAL-PREP/src/main/java/com/example/labfinal/security/SecurityConfig.java)

This is the central configuration class wiring filters, authorization rules, and authentication providers together.

```java
package com.example.labfinal.security;

import com.example.labfinal.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/login", "/error").permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/**").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/dashboard", "/cases/**", "/students/**", "/external-users/**", "/ai/**", "/api/**").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
```

### Explaining the Configuration Directives:
- **`@EnableWebSecurity` & `@EnableMethodSecurity`:** Enables Spring Web Security and allows `@PreAuthorize("hasRole('ADMIN')")` on method levels.
- **`csrf.ignoringRequestMatchers("/api/**")`:** REST APIs use stateless JWT Bearer headers, so CSRF tokens are not needed for `/api/**`.
- **`addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)`:** Places our JWT filter before the standard username/password filter so token requests are authenticated upfront.
- **`authorizeHttpRequests`:**
  - `permitAll()`: Public static assets (`/css/**`), login page (`/login`), and authentication endpoints (`/api/v1/auth/**`).
  - `hasRole("ADMIN")`: Destructive operations like `DELETE /api/v1/**` and `/admin/**`.
  - `authenticated()`: Protected views (`/dashboard`, `/students/**`, `/cases/**`, `/ai/**`).
- **Dual Authentication Mechanism:**
  - **Browser Users:** Uses `.formLogin(...)` with session cookie (`JSESSIONID`).
  - **REST API Users:** Uses Bearer JWT token evaluated by `JwtAuthenticationFilter`.

---

## Step 7: REST API Token Generation Endpoint
### File: [AuthRestController.java](file:///c:/Siam/Git%20Projects/Advance-Java-Practice/LAB-FINAL-PREP/src/main/java/com/example/labfinal/controller/AuthRestController.java)

This REST controller exposes the endpoint for clients (Postman, mobile apps, or JavaScript) to log in and receive a JWT.

```java
package com.example.labfinal.controller;

import com.example.labfinal.dto.LoginRequest;
import com.example.labfinal.dto.LoginResponse;
import com.example.labfinal.security.jwt.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        LoginResponse response = LoginResponse.builder()
                .token(token)
                .type("Bearer")
                .username(userDetails.getUsername())
                .roles(roles)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDetails> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.notFound().build();
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        return ResponseEntity.ok(userDetails);
    }
}
```

---

## Complete End-to-End Serial Execution Flow

### Scenario A: Client Acquires Token
1. Client sends `POST /api/v1/auth/login` with `{ "username": "admin", "password": "admin123" }`.
2. `AuthRestController` calls `authenticationManager.authenticate(...)`.
3. `CustomAuthenticationProvider` loads account via `UserDetailsServiceImpl` and verifies BCrypt hash.
4. If correct, `JwtUtils.generateToken(userDetails)` signs a new token.
5. Server responds with HTTP 200 containing the `token`.

### Scenario B: Client Calls Secured API (`/api/v1/ai/chat`)
1. Client sends `POST /api/v1/ai/chat` with header:
   `Authorization: Bearer eyJhbGciOi...`
2. `JwtAuthenticationFilter` intercepts the request.
3. `JwtUtils.extractUsername(jwt)` extracts `"admin"`.
4. `JwtUtils.isTokenValid(jwt, userDetails)` verifies signature and expiration.
5. Filter creates a `UsernamePasswordAuthenticationToken` and places it in `SecurityContextHolder`.
6. `SecurityConfig` verifies that the user is authenticated.
7. Request successfully arrives at `AiRestController.chat(...)` and returns response.
8. When the HTTP request finishes, `SecurityContext` is cleared for the thread (completely stateless).

---

## Quick Reference Summary Table

| Class | Type | Responsibility |
| :--- | :--- | :--- |
| [PasswordEncoderConfig](file:///c:/Siam/Git%20Projects/Advance-Java-Practice/LAB-FINAL-PREP/src/main/java/com/example/labfinal/config/PasswordEncoderConfig.java) | Config | Exposes `BCryptPasswordEncoder` bean without circular dependency |
| [UserDetailsServiceImpl](file:///c:/Siam/Git%20Projects/Advance-Java-Practice/LAB-FINAL-PREP/src/main/java/com/example/labfinal/security/UserDetailsServiceImpl.java) | Service | Loads user entity from MySQL and converts roles into `GrantedAuthority` |
| [CustomAuthenticationProvider](file:///c:/Siam/Git%20Projects/Advance-Java-Practice/LAB-FINAL-PREP/src/main/java/com/example/labfinal/security/CustomAuthenticationProvider.java) | Provider | Compares raw password with BCrypt hash using `passwordEncoder.matches()` |
| [JwtUtils](file:///c:/Siam/Git%20Projects/Advance-Java-Practice/LAB-FINAL-PREP/src/main/java/com/example/labfinal/security/jwt/JwtUtils.java) | Utility | Generates, parses, extracts claims, and validates HMAC-SHA256 tokens |
| [JwtAuthenticationFilter](file:///c:/Siam/Git%20Projects/Advance-Java-Practice/LAB-FINAL-PREP/src/main/java/com/example/labfinal/security/jwt/JwtAuthenticationFilter.java) | Filter | Intercepts `Authorization: Bearer <token>` and sets authentication in `SecurityContextHolder` |
| [SecurityConfig](file:///c:/Siam/Git%20Projects/Advance-Java-Practice/LAB-FINAL-PREP/src/main/java/com/example/labfinal/security/SecurityConfig.java) | Config | Configures filter chain, permits public endpoints, and enforces role access |
| [AuthRestController](file:///c:/Siam/Git%20Projects/Advance-Java-Practice/LAB-FINAL-PREP/src/main/java/com/example/labfinal/controller/AuthRestController.java) | Controller | Exposes `POST /api/v1/auth/login` to authenticate and return JWT to API clients |
