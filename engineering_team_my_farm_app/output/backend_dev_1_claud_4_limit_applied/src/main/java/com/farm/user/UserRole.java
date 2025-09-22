package com.farm.user;

/**
 * Enumeration defining the different roles a user can have in the farm application.
 * 
 * This enum is used for role-based access control (RBAC) to determine what
 * actions and resources each user can access.
 * 
 * @author Backend Developer 1
 * @version 1.0.0
 */
public enum UserRole {
    
    /**
     * Administrator role with full access to all application features.
     * 
     * Admins can:
     * - Manage products and categories
     * - View and manage all orders
     * - Access user management
     * - Generate reports
     * - Manage media content
     * - Configure application settings
     */
    ADMIN("Administrator", "Full access to all application features"),
    
    /**
     * Customer role with limited access to customer-facing features.
     * 
     * Customers can:
     * - View products and categories
     * - Place orders and make reservations
     * - Manage their own profile
     * - View their order history
     * - Leave reviews and feedback
     */
    CUSTOMER("Customer", "Access to customer features and order management");
    
    private final String displayName;
    private final String description;
    
    /**
     * Constructor for UserRole enum.
     * 
     * @param displayName the human-readable name for the role
     * @param description a description of the role's permissions
     */
    UserRole(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    /**
     * Gets the display name of the role.
     * 
     * @return the human-readable role name
     */
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * Gets the description of the role's permissions.
     * 
     * @return the role description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Gets the Spring Security authority name for this role.
     * 
     * @return the authority name prefixed with "ROLE_"
     */
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
    
    /**
     * Checks if this role is an admin role.
     * 
     * @return true if this role is ADMIN
     */
    public boolean isAdmin() {
        return this == ADMIN;
    }
    
    /**
     * Checks if this role is a customer role.
     * 
     * @return true if this role is CUSTOMER
     */
    public boolean isCustomer() {
        return this == CUSTOMER;
    }
}