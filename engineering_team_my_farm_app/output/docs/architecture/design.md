# System Overview
This document describes the architecture and design of the Farm Web Application, outlining module boundaries, flows, error handling, and logging strategies.

## Diagrams
1. **System Architecture**: The app consists of a frontend built with Angular 20, utilizing standalone components, and a RESTful backend developed in Spring Boot 3.x with Java 21.

2. **Module Boundaries**: 
    - **Frontend Modules**: Farm Overview, Media Gallery, Product Catalog, Product Reservations and Orders, Social Media Integration, Contact Form, Feedback/Review.
    - **Backend Modules**: User Management, Product Management, Order Management, Media Management, Social Media Integration, Reporting.

## Flows
1. **Authentication Flow**: JWT/OAuth2 for user login, including Google and additional providers. 
2. **Checkout Flow**: Steps from product selection to order confirmation.

## Error Handling
ErrorResponses should be consistent across the application, pertaining to different error cases such as validation, server errors, etc.

## Logging Strategy
Utilize SLF4J for backend logging and a centralized logging service for monitoring applications.