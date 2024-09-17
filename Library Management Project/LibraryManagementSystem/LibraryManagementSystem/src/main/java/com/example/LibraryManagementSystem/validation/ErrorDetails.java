package com.example.LibraryManagementSystem.validation;

import java.time.LocalDateTime;

public class ErrorDetails {
    private int status;
    private String message;
    private String details;
    private LocalDateTime timestamp;

    public ErrorDetails(int status, String message, String details) {
        this.status = status;
        this.message = message;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public String getDetails() { return details; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
