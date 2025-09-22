# Local Development Instructions for Farm Web Application

## Environment Setup
1. **Java Version**: Make sure to have Java 21 installed.
2. **Spring Boot Version**: Use Spring Boot 3.x for the backend.
3. **Angular Version**: Ensure Angular CLI is updated to v20.

## Environment Variables
- Set the following environment variables in your .env file:
    - `DB_URL` = "jdbc:mysql://localhost:3306/farmdb"
    - `DB_USERNAME` = "username"
    - `DB_PASSWORD` = "password"
    - `JWT_SECRET` = "your_jwt_secret"

## Ports
- Backend will run on **localhost:8080**.
- Frontend will run on **localhost:4200**.

## Service URLs
- The backend API URL will be **http://localhost:8080/api/**.
- Ensure that you have a local MySQL database setup for development.