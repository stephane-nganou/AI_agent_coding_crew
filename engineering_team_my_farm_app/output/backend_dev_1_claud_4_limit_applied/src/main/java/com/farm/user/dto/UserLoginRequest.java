package com.farm.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for user login requests.
 * 
 * This DTO contains the credentials required for user authentication
 * in the farm application system.
 * 
 * @author Backend Developer 1
 * @version 1.0.0
 */
public class UserLoginRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private boolean rememberMe = false;

    /**
     * Default constructor.
     */
    public UserLoginRequest() {}

    /**
     * Constructor with email and password.
     * 
     * @param email the user's email address
     * @param password the user's password
     */
    public UserLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Constructor with all fields.
     * 
     * @param email the user's email address
     * @param password the user's password
     * @param rememberMe whether to remember the user
     */
    public UserLoginRequest(String email, String password, boolean rememberMe) {
        this.email = email;
        this.password = password;
        this.rememberMe = rememberMe;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public String toString() {
        return "UserLoginRequest{" +
                "email='" + email + '\'' +
                ", rememberMe=" + rememberMe +
                '}';
    }
}