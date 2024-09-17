package com.example.LibraryManagementSystem.validation;

public class LibrarianNotFoundException extends RuntimeException {
    public LibrarianNotFoundException(String message) {
        super(message);
    }
}
