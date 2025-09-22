package com.farmapp.user;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for user registration requests.
 * 
 * Contains validation annotations to ensure proper input validation
 * for user registration operations.
 * 
 * @author Backend Developer 1
 * @version 1.0.0
 */
public class UserRegistrationRequest {

    /**
     * The desired username for the new account.
     * Must be unique and follow username conventions.
     */
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores")
    private String username;

    /**
     * The password for the new account.
     * Must meet minimum security requirements.
     */
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    /**
     * The email address for the new account.
     * Must be unique and follow valid email format.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    /**
     * Default constructor for JSON deserialization.
     */
    public UserRegistrationRequest() {
    }

    /**
     * Constructor with all required fields.
     * 
     * @param username the username
     * @param password the password
     * @param email the email address
     */
    public UserRegistrationRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserRegistrationRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                // Explicitly exclude password from toString for security
                '}';
    }
}