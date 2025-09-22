package com.farmapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Main Spring Boot application class for the Farm Web Application.
 * 
 * This class serves as the entry point for the entire backend application,
 * configuring Spring Boot components, security settings, and CORS policies.
 * 
 * @author Backend Developer 1
 * @version 1.0.0
 */
@SpringBootApplication
public class FarmWebApplication {

    /**
     * Main method to start the Spring Boot application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(FarmWebApplication.class, args);
    }

    /**
     * Password encoder bean for secure password hashing.
     * Uses BCrypt algorithm as recommended by security best practices.
     * 
     * @return BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * CORS configuration to allow cross-origin requests from frontend.
     * Configured to allow requests from specific origins as per security guidelines.
     * 
     * @return WebMvcConfigurer for CORS settings
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOriginPatterns("http://localhost:4200", "https://*.farmapp.com")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}