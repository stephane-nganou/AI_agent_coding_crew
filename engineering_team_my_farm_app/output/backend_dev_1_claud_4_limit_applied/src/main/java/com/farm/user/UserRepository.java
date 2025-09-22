package com.farm.user;

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
 * This interface extends JpaRepository to provide CRUD operations and custom
 * query methods for user management in the farm application.
 * 
 * @author Backend Developer 1
 * @version 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address.
     * 
     * @param email the user's email address
     * @return an Optional containing the user if found, empty otherwise
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their OAuth provider and OAuth ID.
     * 
     * @param oauthProvider the OAuth provider (e.g., "google")
     * @param oauthId the OAuth provider's user ID
     * @return an Optional containing the user if found, empty otherwise
     */
    Optional<User> findByOauthProviderAndOauthId(String oauthProvider, String oauthId);

    /**
     * Finds users by their role.
     * 
     * @param role the user role to search for
     * @return a list of users with the specified role
     */
    List<User> findByRole(UserRole role);

    /**
     * Finds users by their enabled status.
     * 
     * @param enabled the enabled status to search for
     * @return a list of users with the specified enabled status
     */
    List<User> findByEnabled(boolean enabled);

    /**
     * Checks if a user exists with the given email address.
     * 
     * @param email the email address to check
     * @return true if a user exists with the email, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Checks if a user exists with the given OAuth provider and ID.
     * 
     * @param oauthProvider the OAuth provider
     * @param oauthId the OAuth provider's user ID
     * @return true if a user exists with the OAuth credentials, false otherwise
     */
    boolean existsByOauthProviderAndOauthId(String oauthProvider, String oauthId);

    /**
     * Finds users created within a specific date range.
     * 
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return a list of users created within the date range
     */
    @Query("SELECT u FROM User u WHERE u.createdAt BETWEEN :startDate AND :endDate")
    List<User> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                     @Param("endDate") LocalDateTime endDate);

    /**
     * Finds users who have logged in within a specific date range.
     * 
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return a list of users who logged in within the date range
     */
    @Query("SELECT u FROM User u WHERE u.lastLogin BETWEEN :startDate AND :endDate")
    List<User> findByLastLoginBetween(@Param("startDate") LocalDateTime startDate,
                                     @Param("endDate") LocalDateTime endDate);

    /**
     * Finds users by partial email match (case-insensitive).
     * 
     * @param email the partial email to search for
     * @return a list of users whose email contains the given string
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))")
    List<User> findByEmailContainingIgnoreCase(@Param("email") String email);

    /**
     * Finds users by partial first name or last name match (case-insensitive).
     * 
     * @param name the partial name to search for
     * @return a list of users whose first name or last name contains the given string
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> findByNameContainingIgnoreCase(@Param("name") String name);

    /**
     * Counts the total number of users by role.
     * 
     * @param role the user role to count
     * @return the number of users with the specified role
     */
    long countByRole(UserRole role);

    /**
     * Counts the total number of enabled users.
     * 
     * @param enabled the enabled status to count
     * @return the number of users with the specified enabled status
     */
    long countByEnabled(boolean enabled);

    /**
     * Counts users registered within a specific date range.
     * 
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return the number of users registered within the date range
     */
    @Query("SELECT COUNT(u) FROM User u WHERE u.createdAt BETWEEN :startDate AND :endDate")
    long countByCreatedAtBetween(@Param("startDate") LocalDateTime startDate,
                                @Param("endDate") LocalDateTime endDate);

    /**
     * Finds users who have never logged in (lastLogin is null).
     * 
     * @return a list of users who have never logged in
     */
    @Query("SELECT u FROM User u WHERE u.lastLogin IS NULL")
    List<User> findUsersWhoNeverLoggedIn();

    /**
     * Finds inactive users who haven't logged in for a specified number of days.
     * 
     * @param daysAgo the number of days ago to check from
     * @return a list of users who haven't logged in for the specified days
     */
    @Query("SELECT u FROM User u WHERE u.lastLogin < :daysAgo OR u.lastLogin IS NULL")
    List<User> findInactiveUsers(@Param("daysAgo") LocalDateTime daysAgo);

    /**
     * Updates the last login timestamp for a user.
     * 
     * @param userId the user's ID
     * @param lastLogin the new last login timestamp
     */
    @Query("UPDATE User u SET u.lastLogin = :lastLogin WHERE u.id = :userId")
    void updateLastLogin(@Param("userId") Long userId, @Param("lastLogin") LocalDateTime lastLogin);
}