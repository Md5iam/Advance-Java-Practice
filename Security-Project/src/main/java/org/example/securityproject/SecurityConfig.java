package org.example.securityproject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
// before controller layer
public class SecurityConfig {

    @Bean // work like singleTon( only one time intialize )
    public SecurityFilterChain filer(HttpSecurity http){
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/sign-up").permitAll()
                .requestMatchers("/privacy").permitAll()
                .requestMatchers("/admin/**").denyAll()
                .requestMatchers("/payment-*").fullyAuthenticated()
                .anyRequest().authenticated())
                .formLogin(form ->
                        form.loginPage("/sign-in")
                                .failureUrl("/privacy")
                                .usernameParameter("mobile")
                                .passwordParameter("pin")
                                .defaultSuccessUrl("/dashboard")
                                .permitAll()

                )
                .logout(Customizer.withDefaults())
                .rememberMe(Customizer.withDefaults());

        return http.build();
    }
}
