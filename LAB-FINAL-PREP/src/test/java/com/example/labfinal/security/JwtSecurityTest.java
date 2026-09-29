package com.example.labfinal.security;

import com.example.labfinal.security.jwt.JwtUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@SpringBootTest
class JwtSecurityTest {

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    void testJwtTokenGenerationAndValidation() {
        UserDetails userDetails = new User("tester", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        String token = jwtUtils.generateToken(userDetails);

        Assertions.assertNotNull(token);
        Assertions.assertFalse(token.isBlank());
        Assertions.assertEquals("tester", jwtUtils.extractUsername(token));
        Assertions.assertTrue(jwtUtils.isTokenValid(token, userDetails));
    }

    @Test
    void testJwtTokenInvalidForDifferentUser() {
        UserDetails userDetails1 = new User("alice", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        UserDetails userDetails2 = new User("bob", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));

        String token = jwtUtils.generateToken(userDetails1);

        Assertions.assertFalse(jwtUtils.isTokenValid(token, userDetails2));
    }
}
