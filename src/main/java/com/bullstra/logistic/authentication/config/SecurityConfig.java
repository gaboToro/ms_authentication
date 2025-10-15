package com.bullstra.logistic.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println(">>> CARGANDO CONFIGURACIÓN DE SEGURIDAD DEL AUTH SERVICE <<<");

        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/api/v1/auth/**").permitAll() // Allow public access to auth endpoints
                                .anyRequest().authenticated() // All other requests require authentication
                )
                // Configure session management
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS)); // Use stateless sessions (for JWT/API)

        return http.build();
    }
}