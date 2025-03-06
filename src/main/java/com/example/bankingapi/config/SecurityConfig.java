package com.example.bankingapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Permit access to Swagger UI and API documentation endpoints
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                // Permit access to your API endpoints (optional for demo)
                .requestMatchers("/api/**").permitAll()
                // Require authentication for all other requests
                .anyRequest().authenticated()
            )
            // Disable CSRF for simplicity (optional, depending on your needs)
            .csrf().disable()
            // Use form login (optional, only if you want a login page for secured endpoints)
            .formLogin();

        return http.build();
    }
}