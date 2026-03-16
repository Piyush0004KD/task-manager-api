package com.example.taskmanager.task;

public class InvalidDueDateException extends RuntimeException {
    public InvalidDueDateException(String message) {
        super(message);
    }
}
