# Todo API Testing Script

This repository contains a shell script for testing the Todo application's REST API endpoints.

## Overview

The `api_test.sh` script tests all CRUD (Create, Read, Update, Delete) operations for the Todo API, including error handling. It provides colored output for better readability and clear test results.

## Prerequisites

- Bash shell environment
- curl command-line tool
- Running Todo API server

## API Endpoints Tested

The script tests the following API endpoints:

1. `GET /api/todos` - Retrieve all todos
2. `POST /api/todos` - Create a new todo
3. `GET /api/todos/{id}` - Retrieve a specific todo
4. `PUT /api/todos/{id}` - Update a todo
5. `DELETE /api/todos/{id}` - Delete a todo

## Setup

1. Make sure the Todo API server is running (default: http://localhost:8080)
2. If your API server is running on a different URL, update the `API_BASE_URL` variable in the script

## Usage

1. Make the script executable (if not already):
   ```
   chmod +x api_test.sh
   ```

2. Run the script:
   ```
   ./api_test.sh
   ```

## Test Flow

The script performs the following sequence of tests:

1. Checks if the API server is running
2. Gets all todos
3. Creates a new todo
4. Gets the newly created todo
5. Updates the todo
6. Deletes the todo
7. Verifies the todo was deleted
8. Tests error handling with an invalid ID

## Output

The script provides colored output:
- Green: Successful operations
- Yellow: Information and test headers
- Red: Errors and failures

Example output:
```
Starting API tests for Todo App
Checking if API server is running...
API server is running at http://localhost:8080

=== Testing GET all todos ===
Successfully retrieved all todos
Response: [{"id":1,"title":"Example Todo","description":"This is an example","completed":false,"createdAt":"2023-05-01T10:00:00","updatedAt":"2023-05-01T10:00:00"}]

=== Testing POST create todo ===
Successfully created a new todo
Response: {"id":2,"title":"Test Todo 1683123456","description":"This is a test todo created by the API test script","completed":false,"createdAt":"2023-05-03T15:30:56","updatedAt":"2023-05-03T15:30:56"}
Created todo ID: 2

...

=== API Testing Complete ===
```

## Customization

You can modify the script to:
- Change the API base URL
- Add more test cases
- Adjust the output formatting
- Test additional error scenarios

## Troubleshooting

If the script fails to connect to the API server:
1. Ensure the server is running
2. Check that the API_BASE_URL is correct
3. Verify network connectivity
4. Check for any firewall or security restrictions