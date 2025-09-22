package com.farm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Main Spring Boot application class for the Farm Web Application.
 * 
 * This application provides a comprehensive farm management system with:
 * - User authentication and authorization (JWT/OAuth2)
 * - Product catalog management
 * - Order processing and payment integration
 * - Media management for farm photos and videos
 * - Sales reporting and analytics
 * - Social media integration
 * 
 * @author Farm Development Team
 * @version 1.0.0
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class FarmWebApplication {

    /**
     * Main method to start the Spring Boot application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(FarmWebApplication.class, args);
    }
}