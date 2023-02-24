package com.example.RestCalculator.exceptions;

public class FileIsEmptyException extends RuntimeException {
    public FileIsEmptyException(String message) {
        super(message);
    }
}
