#!/bin/bash

# API Testing Script for Todo App
# This script tests the CRUD operations for the Todo API

# Configuration
API_BASE_URL="http://localhost:8080"
CONTENT_TYPE="Content-Type: application/json"

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[0;33m'
NC='\033[0m' # No Color

# Helper function to print colored output
print_message() {
  local color=$1
  local message=$2
  echo -e "${color}${message}${NC}"
}

# Helper function to check if the API server is running
check_server() {
  print_message "$YELLOW" "Checking if API server is running..."
  
  if curl -s --head "$API_BASE_URL" > /dev/null; then
    print_message "$GREEN" "API server is running at $API_BASE_URL"
    return 0
  else
    print_message "$RED" "API server is not running at $API_BASE_URL"
    print_message "$YELLOW" "Please start the server and try again"
    return 1
  fi
}

# Test: Get all todos
test_get_all_todos() {
  print_message "$YELLOW" "\n=== Testing GET all todos ==="
  
  response=$(curl -s -X GET "$API_BASE_URL/api/todos")
  
  if [ $? -eq 0 ]; then
    print_message "$GREEN" "Successfully retrieved all todos"
    print_message "$GREEN" "Response: $response"
    return 0
  else
    print_message "$RED" "Failed to retrieve todos"
    return 1
  fi
}

# Test: Create a new todo
test_create_todo() {
  print_message "$YELLOW" "\n=== Testing POST create todo ==="
  
  local title="Test Todo $(date +%s)"
  local description="This is a test todo created by the API test script"
  
  response=$(curl -s -X POST "$API_BASE_URL/api/todos" \
    -H "$CONTENT_TYPE" \
    -d "{\"title\":\"$title\",\"description\":\"$description\",\"completed\":false}")
  
  if [ $? -eq 0 ] && [[ "$response" == *"$title"* ]]; then
    print_message "$GREEN" "Successfully created a new todo"
    print_message "$GREEN" "Response: $response"
    
    # Extract the ID for later tests
    todo_id=$(echo "$response" | grep -o '"id":[0-9]*' | grep -o '[0-9]*')
    print_message "$GREEN" "Created todo ID: $todo_id"
    echo "$todo_id"
    return 0
  else
    print_message "$RED" "Failed to create todo"
    print_message "$RED" "Response: $response"
    return 1
  fi
}

# Test: Get a specific todo
test_get_todo() {
  local id=$1
  print_message "$YELLOW" "\n=== Testing GET todo with ID $id ==="
  
  response=$(curl -s -X GET "$API_BASE_URL/api/todos/$id")
  
  if [ $? -eq 0 ] && [[ "$response" == *"\"id\":$id"* ]]; then
    print_message "$GREEN" "Successfully retrieved todo with ID $id"
    print_message "$GREEN" "Response: $response"
    return 0
  else
    print_message "$RED" "Failed to retrieve todo with ID $id"
    print_message "$RED" "Response: $response"
    return 1
  fi
}

# Test: Update a todo
test_update_todo() {
  local id=$1
  print_message "$YELLOW" "\n=== Testing PUT update todo with ID $id ==="
  
  local updated_title="Updated Todo $(date +%s)"
  local updated_description="This todo was updated by the API test script"
  
  response=$(curl -s -X PUT "$API_BASE_URL/api/todos/$id" \
    -H "$CONTENT_TYPE" \
    -d "{\"title\":\"$updated_title\",\"description\":\"$updated_description\",\"completed\":true}")
  
  if [ $? -eq 0 ] && [[ "$response" == *"$updated_title"* ]] && [[ "$response" == *"\"completed\":true"* ]]; then
    print_message "$GREEN" "Successfully updated todo with ID $id"
    print_message "$GREEN" "Response: $response"
    return 0
  else
    print_message "$RED" "Failed to update todo with ID $id"
    print_message "$RED" "Response: $response"
    return 1
  fi
}

# Test: Delete a todo
test_delete_todo() {
  local id=$1
  print_message "$YELLOW" "\n=== Testing DELETE todo with ID $id ==="
  
  response=$(curl -s -X DELETE -w "%{http_code}" "$API_BASE_URL/api/todos/$id")
  
  if [ $? -eq 0 ] && [[ "$response" == "204" ]]; then
    print_message "$GREEN" "Successfully deleted todo with ID $id"
    return 0
  else
    print_message "$RED" "Failed to delete todo with ID $id"
    print_message "$RED" "Response code: $response"
    return 1
  fi
}

# Test: Verify todo was deleted
test_verify_deletion() {
  local id=$1
  print_message "$YELLOW" "\n=== Verifying deletion of todo with ID $id ==="
  
  response=$(curl -s -X GET -w "%{http_code}" "$API_BASE_URL/api/todos/$id")
  
  if [ $? -eq 0 ] && [[ "$response" == *"404"* ]]; then
    print_message "$GREEN" "Verified todo with ID $id was deleted (404 Not Found)"
    return 0
  else
    print_message "$RED" "Todo with ID $id still exists or unexpected response"
    print_message "$RED" "Response: $response"
    return 1
  fi
}

# Test: Error handling - Invalid ID
test_invalid_id() {
  print_message "$YELLOW" "\n=== Testing error handling - Invalid ID ==="
  
  response=$(curl -s -X GET -w "%{http_code}" "$API_BASE_URL/api/todos/invalid")
  
  if [ $? -eq 0 ] && [[ "$response" == *"400"* ]]; then
    print_message "$GREEN" "Server correctly returned 400 Bad Request for invalid ID"
    return 0
  else
    print_message "$RED" "Unexpected response for invalid ID"
    print_message "$RED" "Response: $response"
    return 1
  fi
}

# Main test execution
main() {
  print_message "$YELLOW" "Starting API tests for Todo App"
  
  # Check if server is running
  check_server
  if [ $? -ne 0 ]; then
    exit 1
  fi
  
  # Run tests
  test_get_all_todos
  
  # Create a todo and get its ID
  todo_id=$(test_create_todo)
  
  # Only continue if we have a valid ID
  if [[ "$todo_id" =~ ^[0-9]+$ ]]; then
    test_get_todo "$todo_id"
    test_update_todo "$todo_id"
    test_delete_todo "$todo_id"
    test_verify_deletion "$todo_id"
  else
    print_message "$RED" "Could not get a valid todo ID, skipping remaining tests"
  fi
  
  # Test error handling
  test_invalid_id
  
  print_message "$YELLOW" "\n=== API Testing Complete ==="
}

# Run the main function
main