package com.bullstra.logistic.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

// Exclude the default in-memory user details service
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class AuthenticationApplication {

    public static void main(String[] args) {
        // Launches the application
        SpringApplication.run(AuthenticationApplication.class, args);
    }

}