# Task Management API

A RESTful task management service built with **Spring Boot 4** and **Java 21**. It supports full CRUD on tasks, filtering by status, request validation, and consistent JSON error responses.

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.0 (Web MVC, Data JPA, Validation) |
| Database | H2 (in-memory) |
| Build | Maven (wrapper included) |
| Boilerplate | Lombok |
| Testing | JUnit 5, Mockito, `@WebMvcTest` |

## Project Structure

The code is organized **by feature**, not by layer, so everything about tasks lives together:

```
src/main/java/com/example/task_management
├── common
│   ├── dto/ErrorResponse.java            # Uniform error body
│   └── exception/GlobalExceptionHandler.java
└── task
    ├── controller/TaskController.java    # REST endpoints
    ├── dto/TaskRequest.java, TaskResponse.java
    ├── entity/Task.java                  # JPA entity
    ├── enums/TaskStatus.java, TaskPriority.java
    ├── exception/TaskNotFoundException.java
    ├── repository/TaskRepository.java
    └── service/TaskService.java, TaskServiceImpl.java
```

## Getting Started

**Prerequisite:** JDK 21

```bash
git clone https://github.com/FatemaTujZohra20/spring-boot-task-management.git
cd spring-boot-task-management
./mvnw spring-boot:run        # Windows: mvnw.cmd spring-boot:run
```

The API starts on `http://localhost:8080`.

**H2 console:** `http://localhost:8080/h2-console`
JDBC URL `jdbc:h2:mem:taskdb`, user `sa`, empty password.

## API Endpoints

Base path: `/api/tasks`

| Method | Endpoint | Description | Success |
|---|---|---|---|
| `POST` | `/api/tasks` | Create a task | `201 Created` |
| `GET` | `/api/tasks` | List all tasks | `200 OK` |
| `GET` | `/api/tasks/{id}` | Get a task by ID | `200 OK` |
| `PUT` | `/api/tasks/{id}` | Update a task | `200 OK` |
| `DELETE` | `/api/tasks/{id}` | Delete a task | `204 No Content` |
| `GET` | `/api/tasks/status/{status}` | Filter tasks by status | `200 OK` |

**Status values:** `TODO`, `IN_PROGRESS`, `COMPLETED`
**Priority values:** `LOW`, `MEDIUM`, `HIGH`

### Example: create a task

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
        "title": "Write README",
        "description": "Document setup and endpoints",
        "status": "TODO",
        "priority": "HIGH"
      }'
```

Response `201 Created`:

```json
{
  "id": 1,
  "title": "Write README",
  "description": "Document setup and endpoints",
  "status": "TODO",
  "priority": "HIGH"
}
```

### Validation rules

| Field | Rule |
|---|---|
| `title` | Required, max 100 characters |
| `description` | Optional, max 500 characters |
| `status` | Required |
| `priority` | Required |

### Error responses

All handled errors return a consistent body:

```json
{ "message": "Task not found with id: 42" }
```

| Situation | Status |
|---|---|
| Task ID does not exist | `404 Not Found` |
| Request body fails validation | `400 Bad Request` |

## Design Decisions

- **DTOs separate from the entity:** `TaskRequest` / `TaskResponse` define the API contract, so the database model can change without breaking clients.
- **Service interface + implementation:** controllers depend on `TaskService`, keeping business logic swappable and easy to mock in tests.
- **Centralized exception handling:** `@RestControllerAdvice` turns exceptions into clean HTTP responses instead of stack traces.
- **Enums stored as strings:** `@Enumerated(EnumType.STRING)` keeps database values readable and safe if enum order changes.

## Running Tests

```bash
./mvnw test
```

Includes service unit tests (Mockito) and controller slice tests (`@WebMvcTest`).

## Roadmap

- [ ] Return all validation errors, and handle invalid enum values / malformed JSON as `400`
- [ ] `@Transactional` boundaries on service methods
- [ ] Pagination for `GET /api/tasks`
- [ ] `createdAt` / `updatedAt` auditing
- [ ] OpenAPI / Swagger UI
- [ ] PostgreSQL + Flyway, Docker Compose
- [ ] GitHub Actions CI