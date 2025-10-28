# Todo App - Backend Server

## Overview

This is the backend server for the Todo App, a simple task management application. It provides a RESTful API for the Android client to store and retrieve todo items.

## Features

- RESTful API for todo item management
- PostgreSQL database for data persistence
- Kotlin and Ktor for modern, concise server-side code

## Technical Details

### Architecture

The backend follows a layered architecture:
- API Layer (Ktor routing)
- Repository Layer (Data access)
- Database Layer (PostgreSQL with Exposed ORM)

### Dependencies

- **Ktor**: Lightweight web framework for Kotlin
- **Exposed**: Kotlin SQL framework
- **PostgreSQL**: Relational database
- **HikariCP**: High-performance JDBC connection pool
- **Kotlinx Serialization**: JSON serialization/deserialization
- **Logback**: Logging framework

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/todos | Get all todo items |
| GET | /api/todos/{id} | Get a specific todo item |
| POST | /api/todos | Create a new todo item |
| PUT | /api/todos/{id} | Update a todo item |
| DELETE | /api/todos/{id} | Delete a todo item |

## Setup and Installation

### Prerequisites

- JDK 11 or newer
- PostgreSQL 12 or newer
- Kotlin 1.8.10 or newer

### Database Setup

1. Install PostgreSQL
2. Create a database named `todoapp`:
   ```sql
   CREATE DATABASE todoapp;
   ```

### Configuration

The application uses the following environment variables for database configuration:
- `JDBC_DATABASE_URL` (default: `jdbc:postgresql://localhost:5432/todoapp`)
- `JDBC_DATABASE_USERNAME` (default: `postgres`)
- `JDBC_DATABASE_PASSWORD` (default: `postgres`)

### Running the Server

1. Make sure PostgreSQL is running and the database is created
2. Run the server:
   ```
   ./gradlew :backend:run
   ```

## Development

### Project Structure

- `Application.kt`: Main application entry point
- `plugins/`: Ktor plugins and routing
- `models/`: Data classes
- `repositories/`: Data access layer
- `db/`: Database configuration and table definitions

### Adding New Features

To add new features to the backend:

1. Create any necessary model classes in the `models` package
2. Add database tables in the `db/tables` package
3. Implement repository methods in the appropriate repository class
4. Add API endpoints in the `plugins/Routing.kt` file

### Testing

Run the tests using:

```
./gradlew :backend:test
```