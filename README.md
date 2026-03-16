# Task Manager API

A secure REST API built with Spring Boot for managing tasks with role-based access control.

## Tech Stack
- Java 17
- Spring Boot 3.2.5
- Spring Security + JWT
- PostgreSQL
- JPA/Hibernate
- Lombok

## Features
- JWT Authentication
- Role-based access (ADMIN/USER)
- Task CRUD operations
- Pagination
- Global Exception Handling
- Input Validation

## API Endpoints

### Auth
| Method | URL | Description |
|--------|-----|-------------|
| PUT | /api/v1/auth/register | Register user |
| PUT | /api/v1/auth/authenticate | Login |

### Tasks
| Method | URL | Description |
|--------|-----|-------------|
| POST | /tasks | Create task |
| GET | /tasks/{id} | Get task by id |
| GET | /tasks?page=0&size=10 | Get all tasks (Admin) |
| GET | /tasks/my?page=0&size=10 | Get my tasks |
| GET | /tasks/overdue | Get overdue tasks |
| GET | /tasks/status?status=PENDING | Filter by status |
| PUT | /tasks/{id} | Update task |
| DELETE | /tasks/{id} | Delete task |

## Setup
1. Clone the repo
2. Create PostgreSQL database named `taskmanager`
3. Update `application.properties` with your DB credentials
4. Run the application
