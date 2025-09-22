package com.farmapp.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity operations.
 * 
 * Provides data access methods for user management including
 * authentication, user lookup, and administrative queries.
 * Extends JpaRepository for standard CRUD operations.
 * 
 * @author Backend Developer 1
 * @version 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by username.
     * Used primarily for authentication and user lookup.
     * 
     * @param username the username to search for
     * @return Optional containing the user if found, empty otherwise
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by email address.
     * Used for password recovery and duplicate email validation.
     * 
     * @param email the email address to search for
     * @return Optional containing the user if found, empty otherwise
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by username or email.
     * Useful for login where users can use either username or email.
     * 
     * @param username the username to search for
     * @param email the email address to search for
     * @return Optional containing the user if found, empty otherwise
     */
    Optional<User> findByUsernameOrEmail(String username, String email);

    /**
     * Checks if a username already exists in the database.
     * Used for user registration validation.
     * 
     * @param username the username to check
     * @return true if username exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Checks if an email address already exists in the database.
     * Used for user registration validation.
     * 
     * @param email the email address to check
     * @return true if email exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Finds all users with a specific role.
     * Used by administrators to manage users by role.
     * 
     * @param role the role to filter by (CUSTOMER or ADMIN)
     * @return List of users with the specified role
     */
    List<User> findByRole(String role);

    /**
     * Finds all enabled users.
     * Used for filtering active users only.
     * 
     * @param enabled the enabled status to filter by
     * @return List of users with the specified enabled status
     */
    List<User> findByEnabled(Boolean enabled);

    /**
     * Finds users created within a specific date range.
     * Used for reporting and analytics.
     * 
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return List of users created within the date range
     */
    @Query("SELECT u FROM User u WHERE u.createdAt BETWEEN :startDate AND :endDate ORDER BY u.createdAt DESC")
    List<User> findUsersCreatedBetween(@Param("startDate") LocalDateTime startDate, 
                                     @Param("endDate") LocalDateTime endDate);

    /**
     * Counts the total number of users by role.
     * Used for dashboard statistics and reporting.
     * 
     * @param role the role to count
     * @return the count of users with the specified role
     */
    @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role")
    Long countByRole(@Param("role") String role);

    /**
     * Finds the most recently registered users.
     * Used for administrative dashboards and user management.
     * 
     * @param limit the maximum number of users to return
     * @return List of the most recently registered users
     */
    @Query("SELECT u FROM User u ORDER BY u.createdAt DESC LIMIT :limit")
    List<User> findRecentUsers(@Param("limit") int limit);

    /**
     * Finds users whose username contains the specified search term.
     * Used for user search functionality.
     * 
     * @param searchTerm the term to search for in usernames
     * @return List of users whose username contains the search term
     */
    @Query("SELECT u FROM User u WHERE u.username LIKE %:searchTerm% ORDER BY u.username")
    List<User> findByUsernameContaining(@Param("searchTerm") String searchTerm);

    /**
     * Finds users whose email contains the specified search term.
     * Used for user search functionality.
     * 
     * @param searchTerm the term to search for in email addresses
     * @return List of users whose email contains the search term
     */
    @Query("SELECT u FROM User u WHERE u.email LIKE %:searchTerm% ORDER BY u.email")
    List<User> findByEmailContaining(@Param("searchTerm") String searchTerm);

    /**
     * Updates the enabled status of a user.
     * Used for enabling/disabling user accounts.
     * 
     * @param userId the ID of the user to update
     * @param enabled the new enabled status
     * @return the number of affected rows
     */
    @Query("UPDATE User u SET u.enabled = :enabled, u.updatedAt = CURRENT_TIMESTAMP WHERE u.userId = :userId")
    int updateEnabledStatus(@Param("userId") Long userId, @Param("enabled") Boolean enabled);
}