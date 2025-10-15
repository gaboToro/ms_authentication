package com.bullstra.logistic.authentication.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class UserAuthDataDTO {
    // Unique identifier for the user
    private UUID id;

    // User's email address
    private String email;

    // Hashed password for verification
    private String passwordHash;

    // Name of the user's role (e.g., 'ADMIN', 'DRIVER')
    private String rolName;
}
