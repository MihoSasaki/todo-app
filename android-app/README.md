# Todo App - Android Client

## Overview

This is the Android client for the Todo App, a simple task management application. It communicates with a Kotlin backend server to store and retrieve todo items.

## Features

- View a list of todo items
- Add new todo items
- Mark todo items as complete/incomplete
- Delete todo items

## Technical Details

### Architecture

The app follows a simple architecture with:
- UI layer (Activities, Adapters)
- Network layer (Retrofit API service)
- Model layer (Data classes)

### Dependencies

- **Retrofit**: For API communication
- **Gson**: For JSON serialization/deserialization
- **Kotlin Coroutines**: For asynchronous operations
- **AndroidX**: For modern Android components
- **RecyclerView**: For displaying lists of data
- **CardView**: For material design cards
- **ConstraintLayout**: For flexible UI layouts

## Setup and Installation

### Prerequisites

- Android Studio Arctic Fox (2020.3.1) or newer
- Android SDK 33 (minimum SDK 24)
- Kotlin 1.8.10 or newer

### Running the App

1. Make sure the backend server is running (see backend README)
2. Open the project in Android Studio
3. Connect an Android device or start an emulator
4. Run the app using the "Run" button in Android Studio

### Configuration

The app is configured to connect to the backend at `http://10.0.2.2:8080/` which is the special IP that allows an Android emulator to connect to the host machine's localhost. If you're running on a physical device or need to connect to a different server, update the base URL in `MainActivity.kt`.

## Development

### Adding New Features

To add new features to the app:

1. Create any necessary model classes in the `models` package
2. Add API endpoints to the `TodoApiService` interface
3. Implement UI components and connect them to the API

### Testing

The app includes basic unit tests. Run them using:

```
./gradlew :android-app:test
```

For UI tests:

```
./gradlew :android-app:connectedAndroidTest
```