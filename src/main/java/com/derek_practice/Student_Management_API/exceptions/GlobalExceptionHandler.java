package com.derek_practice.Student_Management_API.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleBadStudentNotFound(
            StudentNotFoundException exception
    ) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                exception.getErrorId(),
                exception.getErrorMessage(),
                exception.getErrorDetails()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateStudentException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicateStudent(
            DuplicateStudentException exception
    ) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                exception.getErrorId(),
                exception.getErrorMessage(),
                exception.getErrorDetails()
        );

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception
    ) {
        Map<String, Object> errors = new HashMap<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            String fieldName = error.getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        }
        ApiErrorResponse response = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                errors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}

