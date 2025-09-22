package com.farmapp.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for user management operations.
 * 
 * Provides comprehensive user management functionality including
 * registration, authentication, profile management, and administrative operations.
 * Implements UserDetailsService for Spring Security integration.
 * 
 * @author Backend Developer 1
 * @version 1.0.0
 */
@Service
@Transactional
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for dependency injection.
     * 
     * @param userRepository the user repository for data access
     * @param passwordEncoder the password encoder for secure password handling
     */
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        logger.info("UserService initialized with password encoder: {}", 
                   passwordEncoder.getClass().getSimpleName());
    }

    /**
     * Registers a new user in the system.
     * 
     * @param username the desired username
     * @param password the plain text password
     * @param email the user's email address
     * @param role the user's role (CUSTOMER or ADMIN)
     * @return the created User entity
     * @throws IllegalArgumentException if validation fails or user already exists
     */
    public User registerUser(String username, String password, String email, String role) {
        logger.info("Attempting to register new user with username: {} and email: {}", username, email);
        
        try {
            validateUserRegistrationInput(username, password, email, role);
            
            if (userRepository.existsByUsername(username)) {
                logger.warn("Registration failed: Username '{}' already exists", username);
                throw new IllegalArgumentException("Username already exists");
            }
            
            if (userRepository.existsByEmail(email)) {
                logger.warn("Registration failed: Email '{}' already exists", email);
                throw new IllegalArgumentException("Email already exists");
            }
            
            String encodedPassword = passwordEncoder.encode(password);
            User newUser = new User(username, encodedPassword, email, role.toUpperCase());
            
            User savedUser = userRepository.save(newUser);
            logger.info("Successfully registered user with ID: {} and username: {}", 
                       savedUser.getUserId(), savedUser.getUsername());
            
            return savedUser;
            
        } catch (Exception e) {
            logger.error("Error during user registration for username: {}", username, e);
            throw new RuntimeException("Failed to register user: " + e.getMessage(), e);
        }
    }

    /**
     * Authenticates a user by username/email and password.
     * 
     * @param usernameOrEmail the username or email address
     * @param password the plain text password
     * @return true if authentication succeeds, false otherwise
     */
    public boolean authenticateUser(String usernameOrEmail, String password) {
        logger.info("Attempting to authenticate user: {}", usernameOrEmail);
        
        try {
            Optional<User> userOptional = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
            
            if (userOptional.isEmpty()) {
                logger.warn("Authentication failed: User '{}' not found", usernameOrEmail);
                return false;
            }
            
            User user = userOptional.get();
            
            if (!user.isEnabled()) {
                logger.warn("Authentication failed: User '{}' is disabled", usernameOrEmail);
                return false;
            }
            
            boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());
            
            if (passwordMatches) {
                logger.info("Authentication successful for user: {}", usernameOrEmail);
            } else {
                logger.warn("Authentication failed: Invalid password for user: {}", usernameOrEmail);
            }
            
            return passwordMatches;
            
        } catch (Exception e) {
            logger.error("Error during authentication for user: {}", usernameOrEmail, e);
            return false;
        }
    }

    /**
     * UserDetailsService implementation for Spring Security.
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Loading user details for username: {}", username);
        
        return userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> {
                    logger.warn("User not found with username: {}", username);
                    return new UsernameNotFoundException("User not found with username: " + username);
                });
    }

    /**
     * Retrieves all users in the system.
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        logger.info("Retrieving all users");
        
        try {
            List<User> users = userRepository.findAll();
            logger.info("Retrieved {} users", users.size());
            return users;
            
        } catch (Exception e) {
            logger.error("Error retrieving all users", e);
            throw new RuntimeException("Failed to retrieve users", e);
        }
    }

    /**
     * Gets user statistics for reporting.
     */
    @Transactional(readOnly = true)
    public UserStatistics getUserStatistics() {
        logger.info("Generating user statistics");
        
        try {
            Long totalUsers = userRepository.count();
            Long totalCustomers = userRepository.countByRole("CUSTOMER");
            Long totalAdmins = userRepository.countByRole("ADMIN");
            Long enabledUsers = (long) userRepository.findByEnabled(true).size();
            Long disabledUsers = (long) userRepository.findByEnabled(false).size();
            
            UserStatistics stats = new UserStatistics(totalUsers, totalCustomers, 
                                                     totalAdmins, enabledUsers, disabledUsers);
            
            logger.info("Generated user statistics: {}", stats);
            return stats;
            
        } catch (Exception e) {
            logger.error("Error generating user statistics", e);
            throw new RuntimeException("Failed to generate user statistics", e);
        }
    }

    /**
     * Validates user registration input parameters.
     */
    private void validateUserRegistrationInput(String username, String password, String email, String role) {
        if (username == null || username.trim().length() < 3) {
            throw new IllegalArgumentException("Username must be at least 3 characters long");
        }
        
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        
        if (role == null || (!"CUSTOMER".equalsIgnoreCase(role) && !"ADMIN".equalsIgnoreCase(role))) {
            throw new IllegalArgumentException("Role must be either CUSTOMER or ADMIN");
        }
    }
}