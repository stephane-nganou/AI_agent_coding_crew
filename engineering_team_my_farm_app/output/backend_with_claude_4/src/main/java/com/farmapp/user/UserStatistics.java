package com.farmapp.user;

/**
 * Data transfer object for user statistics and metrics.
 * 
 * Used for reporting and dashboard functionality to provide
 * aggregate information about users in the system.
 * 
 * @author Backend Developer 1
 * @version 1.0.0
 */
public class UserStatistics {

    private Long totalUsers;
    private Long totalCustomers;
    private Long totalAdmins;
    private Long enabledUsers;
    private Long disabledUsers;

    /**
     * Default constructor.
     */
    public UserStatistics() {
    }

    /**
     * Constructor with all statistics.
     * 
     * @param totalUsers the total number of users
     * @param totalCustomers the total number of customers
     * @param totalAdmins the total number of admins
     * @param enabledUsers the number of enabled users
     * @param disabledUsers the number of disabled users
     */
    public UserStatistics(Long totalUsers, Long totalCustomers, Long totalAdmins, 
                         Long enabledUsers, Long disabledUsers) {
        this.totalUsers = totalUsers;
        this.totalCustomers = totalCustomers;
        this.totalAdmins = totalAdmins;
        this.enabledUsers = enabledUsers;
        this.disabledUsers = disabledUsers;
    }

    // Getters and setters

    public Long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Long getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(Long totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public Long getTotalAdmins() {
        return totalAdmins;
    }

    public void setTotalAdmins(Long totalAdmins) {
        this.totalAdmins = totalAdmins;
    }

    public Long getEnabledUsers() {
        return enabledUsers;
    }

    public void setEnabledUsers(Long enabledUsers) {
        this.enabledUsers = enabledUsers;
    }

    public Long getDisabledUsers() {
        return disabledUsers;
    }

    public void setDisabledUsers(Long disabledUsers) {
        this.disabledUsers = disabledUsers;
    }

    @Override
    public String toString() {
        return "UserStatistics{" +
                "totalUsers=" + totalUsers +
                ", totalCustomers=" + totalCustomers +
                ", totalAdmins=" + totalAdmins +
                ", enabledUsers=" + enabledUsers +
                ", disabledUsers=" + disabledUsers +
                '}';
    }
}