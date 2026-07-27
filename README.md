# BuyNow Auth Service

## Overview

The Auth Service is responsible for authentication and authorization within the BuyNow microservices architecture. It securely manages user registration, login, JWT token generation, and role-based access control.

## Features

- User Registration
- User Login
- JWT Authentication
- Role-Based Authorization
- Password Encryption (BCrypt)
- Token Validation

## Tech Stack

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT
- MySQL/PostgreSQL
- Maven

## APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | /api/v1/auth/register | Register a user |
| POST | /api/v1/auth/login | Login |
| POST | /api/v1/auth/validate | Validate JWT |

## Author

Ebron Sasack
