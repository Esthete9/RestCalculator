package com.example.RestCalculator.exceptions;

public class FileErrorResponse {
    private final String message;
    private final long timestamp;

    public FileErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }


}
