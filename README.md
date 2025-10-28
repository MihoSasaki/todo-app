# Todo App

A full-stack Todo application with a Kotlin backend and Android frontend.

## Project Structure

This project consists of two main components:

1. **Backend**: A Kotlin-based REST API using Ktor framework with PostgreSQL database
2. **Android App**: A Kotlin Android application that communicates with the backend

For more detailed information about each component, see their respective README files:
- [Backend README](backend/README.md)
- [Android App README](android-app/README.md)

## Backend

The backend is built with:

- Kotlin
- Ktor framework for REST API
- Exposed ORM for database operations
- PostgreSQL for data storage
- HikariCP for connection pooling

### API Endpoints

- `GET /api/todos` - Get all todos
- `GET /api/todos/{id}` - Get a specific todo
- `POST /api/todos` - Create a new todo
- `PUT /api/todos/{id}` - Update a todo
- `DELETE /api/todos/{id}` - Delete a todo

### Running the Backend

1. Make sure PostgreSQL is running and create a database named `todoapp`
2. Set the following environment variables (or use the defaults):
   - `JDBC_DATABASE_URL` (default: `jdbc:postgresql://localhost:5432/todoapp`)
   - `JDBC_DATABASE_USERNAME` (default: `postgres`)
   - `JDBC_DATABASE_PASSWORD` (default: `postgres`)
3. Run the backend:
   ```
   ./gradlew :backend:run
   ```

## Android App

The Android app is built with:

- Kotlin
- Android Jetpack components
- Retrofit for API communication
- Kotlin Coroutines for asynchronous operations
- View Binding for UI

### Features

- View list of todos
- Add new todos
- Mark todos as complete/incomplete
- Delete todos

### Running the Android App

1. Make sure the backend is running
2. Connect an Android device or start an emulator
3. Run the Android app:
   ```
   ./gradlew :android-app:installDebug
   ```

## Development Setup

1. Clone the repository
2. Open the project in Android Studio or IntelliJ IDEA
3. Sync Gradle files
4. Run the backend and Android app as described above

## Database Setup

The application automatically creates the necessary tables when it starts. However, you need to have a PostgreSQL database named `todoapp` available.

To create the database manually:

```sql
CREATE DATABASE todoapp;
```
