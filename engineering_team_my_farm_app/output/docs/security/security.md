# Security Guidelines for Farm Web Application

## JWT/OAuth2 Flows
- Implement OAuth2 for third-party authentication (Google, Facebook, etc.).
- Use JWT tokens for session management and protection of API endpoints.

## Password Policies
- Enforce strong password requirements (minimum 8 characters, uppercase, lowercase, numbers, symbols).
- Use bcrypt to hash passwords before storing them in the database.

## Roles and Permissions
- Define user roles: Admin, Customer.
- Restrict access to certain API endpoints based on user roles (e.g., Admins can manage products and users).

## CORS (Cross-Origin Resource Sharing)
- Enable CORS policies to allow requests only from specified domains (e.g., frontend application).

## Secrets Management
- Store API keys and sensitive information in environment variables.
- Do not hard-code secrets in the source code.

## CSRF Protection
- Utilize CSRF tokens for state-changing operations to prevent cross-site request forgery.

## Session Management
- JWT tokens should have an expiration date and should be securely stored on the client-side.