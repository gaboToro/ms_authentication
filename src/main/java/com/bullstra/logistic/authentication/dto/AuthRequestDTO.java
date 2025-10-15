package com.bullstra.logistic.authentication.dto;

import lombok.Data;

@Data
public class AuthRequestDTO {
    // User's email address for login
    private String email;

    // User's plain-text password for authentication
    private String password;
}