package com.blog.payload;

import java.util.Date;

public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
    public ErrorDetails(Date timestamp, String message, String details) {

        this.timestamp = timestamp;      // Constructor based injection
        this.message = message;
        this.details = details;
    }
    public Date getTimestamp() {       // Getters & Setters
        return timestamp;
    }
    public String getMessage() {
        return message;}
    public String getDetails() {

        return details;
    }
}