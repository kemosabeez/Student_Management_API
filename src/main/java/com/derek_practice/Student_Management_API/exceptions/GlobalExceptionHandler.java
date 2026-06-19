package com.derek_practice.Student_Management_API.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}

