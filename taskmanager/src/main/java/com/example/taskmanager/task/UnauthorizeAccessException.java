package com.example.taskmanager.task;

public class UnauthorizeAccessException extends RuntimeException {
    public UnauthorizeAccessException(String message) {
        super(message);
    }
}
