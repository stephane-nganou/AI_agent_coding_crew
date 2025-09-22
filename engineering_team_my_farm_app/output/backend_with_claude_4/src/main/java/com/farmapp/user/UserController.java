package com.farmapp.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for user management operations.
 * 
 * Provides endpoints for user registration, authentication, profile management,
 * and administrative operations. Implements proper security controls and
 * comprehensive error handling.
 * 
 * @author Backend Developer 1
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://localhost:4200", "https://*.farmapp.com"})
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    /**
     * Constructor for dependency injection.
     * 
     * @param userService the user service for business logic operations
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        logger.info("UserController initialized");
    }

    /**
     * Registers a new user account.
     * 
     * This endpoint allows anonymous access for user registration.
     * Validates input and creates a new user account with encrypted password.
     * 
     * @param registrationRequest the user registration details
     * @return ResponseEntity with success message or error details
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(
            @Valid @RequestBody UserRegistrationRequest registrationRequest) {
        
        logger.info("Received user registration request for username: {}", 
                   registrationRequest.getUsername());
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Register the user with default CUSTOMER role
            User newUser = userService.registerUser(
                registrationRequest.getUsername(),
                registrationRequest.getPassword(),
                registrationRequest.getEmail(),
                "CUSTOMER"
            );
            
            // Return success response without sensitive information
            response.put("success", true);
            response.put("message", "User registered successfully");
            response.put("userId", newUser.getUserId());
            response.put("username", newUser.getUsername());
            response.put("email", newUser.getEmail());
            
            logger.info("Successfully registered user: {}", newUser.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (IllegalArgumentException e) {
            logger.warn("User registration validation failed: {}", e.getMessage());
            response.put("success", false);
            response.put("message", e.getMessage());
            response.put("error", "VALIDATION_ERROR");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            
        } catch (Exception e) {
            logger.error("Unexpected error during user registration", e);
            response.put("success", false);
            response.put("message", "Internal server error during registration");
            response.put("error", "SERVER_ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Authenticates a user with username/email and password.
     * 
     * This endpoint allows anonymous access for authentication.
     * Returns authentication status without exposing sensitive data.
     * 
     * @param loginRequest the user login credentials
     * @return ResponseEntity with authentication result
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> authenticateUser(
            @Valid @RequestBody UserLoginRequest loginRequest) {
        
        logger.info("Received authentication request for user: {}", 
                   loginRequest.getUsernameOrEmail());
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean isAuthenticated = userService.authenticateUser(
                loginRequest.getUsernameOrEmail(),
                loginRequest.getPassword()
            );
            
            if (isAuthenticated) {
                response.put("success", true);
                response.put("message", "Authentication successful");
                response.put("authenticated", true);
                
                logger.info("Authentication successful for user: {}", 
                           loginRequest.getUsernameOrEmail());
                return ResponseEntity.ok(response);
                
            } else {
                response.put("success", false);
                response.put("message", "Invalid credentials");
                response.put("authenticated", false);
                response.put("error", "INVALID_CREDENTIALS");
                
                logger.warn("Authentication failed for user: {}", 
                           loginRequest.getUsernameOrEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
        } catch (Exception e) {
            logger.error("Unexpected error during authentication", e);
            response.put("success", false);
            response.put("message", "Internal server error during authentication");
            response.put("authenticated", false);
            response.put("error", "SERVER_ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Retrieves all users in the system.
     * 
     * This endpoint requires ADMIN role access.
     * Returns a list of all users with basic information (no passwords).
     * 
     * @return ResponseEntity with list of users or error message
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        
        logger.info("Received request to retrieve all users");
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<User> users = userService.getAllUsers();
            
            // Remove sensitive information from response
            List<Map<String, Object>> sanitizedUsers = users.stream()
                .map(this::sanitizeUserData)
                .toList();
            
            response.put("success", true);
            response.put("message", "Users retrieved successfully");
            response.put("users", sanitizedUsers);
            response.put("totalCount", sanitizedUsers.size());
            
            logger.info("Successfully retrieved {} users", sanitizedUsers.size());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error retrieving all users", e);
            response.put("success", false);
            response.put("message", "Failed to retrieve users");
            response.put("error", "SERVER_ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Retrieves user statistics for reporting.
     * 
     * This endpoint requires ADMIN role access.
     * Returns aggregate statistics about users in the system.
     * 
     * @return ResponseEntity with user statistics or error message
     */
    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getUserStatistics() {
        
        logger.info("Received request for user statistics");
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            UserStatistics statistics = userService.getUserStatistics();
            
            response.put("success", true);
            response.put("message", "User statistics retrieved successfully");
            response.put("statistics", statistics);
            
            logger.info("Successfully retrieved user statistics");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error retrieving user statistics", e);
            response.put("success", false);
            response.put("message", "Failed to retrieve user statistics");
            response.put("error", "SERVER_ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Retrieves user information by ID.
     * 
     * This endpoint requires authentication and appropriate authorization.
     * Users can only access their own information unless they are admins.
     * 
     * @param userId the ID of the user to retrieve
     * @return ResponseEntity with user information or error message
     */
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.userId")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long userId) {
        
        logger.info("Received request to retrieve user with ID: {}", userId);
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Note: This would typically use a findById method in the service
            // For now, we'll get all users and filter (not optimal, but functional)
            List<User> users = userService.getAllUsers();
            User user = users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
            
            if (user != null) {
                response.put("success", true);
                response.put("message", "User retrieved successfully");
                response.put("user", sanitizeUserData(user));
                
                logger.info("Successfully retrieved user with ID: {}", userId);
                return ResponseEntity.ok(response);
                
            } else {
                response.put("success", false);
                response.put("message", "User not found");
                response.put("error", "USER_NOT_FOUND");
                
                logger.warn("User not found with ID: {}", userId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
        } catch (Exception e) {
            logger.error("Error retrieving user with ID: {}", userId, e);
            response.put("success", false);
            response.put("message", "Failed to retrieve user");
            response.put("error", "SERVER_ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Global exception handler for handling validation errors.
     * 
     * @param e the exception that was thrown
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(IllegalArgumentException e) {
        
        logger.warn("Validation error: {}", e.getMessage());
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", e.getMessage());
        response.put("error", "VALIDATION_ERROR");
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Global exception handler for handling general exceptions.
     * 
     * @param e the exception that was thrown
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception e) {
        
        logger.error("Unexpected error in UserController", e);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "An unexpected error occurred");
        response.put("error", "SERVER_ERROR");
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * Removes sensitive information from user data before returning in API responses.
     * 
     * @param user the user entity to sanitize
     * @return Map containing sanitized user data
     */
    private Map<String, Object> sanitizeUserData(User user) {
        Map<String, Object> sanitized = new HashMap<>();
        sanitized.put("userId", user.getUserId());
        sanitized.put("username", user.getUsername());
        sanitized.put("email", user.getEmail());
        sanitized.put("role", user.getRole());
        sanitized.put("enabled", user.getEnabled());
        sanitized.put("createdAt", user.getCreatedAt());
        sanitized.put("updatedAt", user.getUpdatedAt());
        // Explicitly exclude password and other sensitive fields
        return sanitized;
    }
}