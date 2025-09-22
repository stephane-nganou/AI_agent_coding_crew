package com.farmapp.user;

import lombok.Data;

/**
 * Request object for user registration.
 */
@Data
public class UserRegistrationRequest {
    private String username;
    private String password;
    private String email;
}