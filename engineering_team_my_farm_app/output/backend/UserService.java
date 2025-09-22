package com.farmapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for user-related operations.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Register a new user.
     * @param request details for user registration
     */
    public void registerUser(UserRegistrationRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        userRepository.save(user);
    }

    /**
     * Get all users.
     * @return List of Users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}