package com.farmapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing users.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Register a new user.
     * @param userRegistrationRequest the user's registration request
     * @return ResponseEntity with status
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        userService.registerUser(userRegistrationRequest);
        return ResponseEntity.ok("User registered successfully!");
    }

    /**
     * Get all users.
     * @return List of users
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}