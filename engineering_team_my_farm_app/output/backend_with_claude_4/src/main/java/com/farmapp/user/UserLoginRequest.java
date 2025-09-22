package com.farmapp.user;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for user login requests.
 * 
 * Contains validation annotations to ensure proper input validation
 * for user authentication operations.
 * 
 * @author Backend Developer 1
 * @version 1.0.0
 */
public class UserLoginRequest {

    /**
     * The username or email address used for login.
     * Users can authenticate with either their username or email.
     */
    @NotBlank(message = "Username or email is required")
    private String usernameOrEmail;

    /**
     * The password for authentication.
     */
    @NotBlank(message = "Password is required")
    private String password;

    /**
     * Default constructor for JSON deserialization.
     */
    public UserLoginRequest() {
    }

    /**
     * Constructor with required fields.
     * 
     * @param usernameOrEmail the username or email
     * @param password the password
     */
    public UserLoginRequest(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }

    // Getters and setters

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginRequest{" +
                "usernameOrEmail='" + usernameOrEmail + '\'' +
                // Explicitly exclude password from toString for security
                '}';
    }
}