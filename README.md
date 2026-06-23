## Student Management API

A Spring Boot practice project for learning REST APIs, DTOs, validation, exception handling, unit testing, and GitHub workflow.

## Base URL

```text
http://localhost:8080
```

## API Endpoints

### 1. Get all students

```http
GET /students
```

Description:

Returns all students.

Sample response:

```json
[
  {
    "id": 1,
    "name": "Derek",
    "course": "BS ECE",
    "yearLevel": 4
  }
]
```

---

### 2. Get student by ID

```http
GET /students/{id}
```

Example:

```http
GET /students/1
```

Description:

Returns one student based on ID.

Sample response:

```json
{
  "id": 1,
  "name": "Derek",
  "course": "BS ECE",
  "yearLevel": 4
}
```

---

### 3. Add student

```http
POST /students
```

Description:

Adds a new student.

Sample request body:

```json
{
  "id": 1,
  "name": "Derek",
  "course": "BS ECE",
  "yearLevel": 4
}
```

Sample response:

```json
{
  "id": 1,
  "name": "Derek",
  "course": "BS ECE",
  "yearLevel": 4
}
```

---

### 4. Update student

```http
PUT /students/{id}
```

Example:

```http
PUT /students/1
```

Description:

Updates an existing student.

Sample request body:

```json
{
  "id": 99,
  "name": "Derek Updated",
  "course": "BS IT",
  "yearLevel": 5
}
```

Sample response:

```json
{
  "id": 1,
  "name": "Derek Updated",
  "course": "BS IT",
  "yearLevel": 5
}
```

Note:

The path ID is used as the actual student ID.
For example, in `PUT /students/1`, student ID `1` will be updated even if the request body contains `"id": 99`.

---

### 5. Delete student

```http
DELETE /students/{id}
```

Example:

```http
DELETE /students/1
```

Description:

Deletes an existing student.

Sample response:

```text
Delete Successful
```

---

## Error Responses

### Student not found

This happens when getting, updating, or deleting a student ID that does not exist.

Sample response:

```json
{
  "errorCode": 404,
  "errorMessage": "Student does not exist",
  "errorDetails": {}
}
```

---

### Duplicate student

This happens when adding a student with an ID that already exists.

Sample response:

```json
{
  "errorCode": 400,
  "errorMessage": "Duplicate Student",
  "errorDetails": {}
}
```

---

### Validation failed

This happens when the request body has invalid values.

Invalid sample request:

```json
{
  "id": 0,
  "name": "",
  "course": "",
  "yearLevel": 10
}
```

Sample response:

```json
{
  "errorCode": 400,
  "errorMessage": "Validation Failed",
  "errorDetails": {
    "id": "ID must be at least 1",
    "name": "Name is required",
    "course": "Course is required",
    "yearLevel": "Year level must be between 1 and 5"
  }
}
```

## Technologies Used

* Java
* Spring Boot
* Spring Web
* Validation
* JUnit
* Git
* GitHub
* Bruno
