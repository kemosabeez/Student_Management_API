package com.derek_practice.Student_Management_API.exceptions;

import java.util.Map;

public class StudentNotFoundException extends RuntimeException {
    private final int errorId;
    private final String errorMessage;
    private final Map<String, Object> errorDetails;

    public StudentNotFoundException(
            int errorId,
            String errorMessage,
            Map<String, Object> errorDetails
    ) {
        super(errorMessage);
        this.errorId = errorId;
        this.errorMessage = errorMessage;
        this.errorDetails = errorDetails;
    }

    public int getErrorId() {
        return errorId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Map<String, Object> getErrorDetails() {
        return errorDetails;
    }
}
