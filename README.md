# Order & User Service

This project is composed of two main modules: **Order Service** and **User Service**, each providing RESTful APIs for order and user management. The services are built with Spring Boot and follow best practices for controller and service layer separation.

---

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [API Endpoints](#api-endpoints)
    - [Order Service](#order-service)
    - [User Service](#user-service)
- [Exception Handling](#exception-handling)
- [Testing](#testing)
- [How to Run](#how-to-run)
- [Dependencies](#dependencies)

---

## Overview

The **Order Service** manages orders, including creation, retrieval, updating, and deletion, and ensures that each order is linked to a valid user by communicating with the User Service.

The **User Service** manages user records, including creation, retrieval, updating, and deletion, and provides validation for the Order Service via a REST client.

---

## Architecture

- **Controller Layer**: Handles HTTP requests and responses.
- **Service Layer**: Business logic and interaction with repositories and external services.
- **Repository Layer**: (Not shown here) Responsible for data persistence.

---

## API Endpoints

### Order Service

Base path: `/orders`

| HTTP Method | Endpoint         | Description               |
|-------------|------------------|---------------------------|
| POST        | `/orders`        | Create a new order        |
| GET         | `/orders/{id}`   | Get order by ID           |
| PUT         | `/orders/{id}`   | Update order by ID        |
| DELETE      | `/orders/{id}`   | Delete order by ID        |

**Order Entity** fields typically: `id`, `userId`, `product`, `quantity`, `price`

#### Example: Create Order

json
POST /orders
{
  "userId": 123,
  "product": "Book",
  "quantity": 2,
  "price": 19.99
}
```

---

### User Service

Base path: `/users`

| HTTP Method | Endpoint         | Description               |
|-------------|------------------|---------------------------|
| POST        | `/users`         | Create a new user         |
| GET         | `/users/{id}`    | Get user by ID            |
| PUT         | `/users/{id}`    | Update user by ID         |
| DELETE      | `/users/{id}`    | Delete user by ID         |

**User Entity** fields typically: `id`, `username`, `email`, `password`

#### Example: Create User

```json
POST /users
{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "securePass"
}
```

---

## Exception Handling

- **OrderNotFoundException**: Returned when an order with the specified ID does not exist.
- **UserNotFoundException**: Returned when a user with the specified ID does not exist.
- Both exceptions are mapped to HTTP 404 responses.

---

## Testing

Unit tests are provided for all controller and service classes using **JUnit 5** and **Mockito**.  
Tests cover normal operation and exceptional cases for all endpoints and business logic.

---

## How to Run

1. **Clone the repository**
2. **Build the project**:
   ```
   ./mvnw clean package
   ```
3. **Run the services**:
   ```
   ./mvnw spring-boot:run
   ```
   (Run both User and Order service if they are in separate modules)

4. **Test the APIs** using tools like Postman or `curl`.

---

## Dependencies

- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- H2 Database (or any other as configured)
- Lombok (optional)
- JUnit 5, Mockito (for testing)

---

## Notes

- The `OrderService` validates the existence of a user via the `ExternalUserClient` before creating an order.
- All logging is handled via SLF4J.
- For simplicity, password handling in User entity should be improved for production (e.g., hashing).
- Custom exceptions are mapped to proper HTTP status codes.

---

## Author

- [Your Name or Team]
- GitHub: [djatwa](https://github.com/djatwa)
