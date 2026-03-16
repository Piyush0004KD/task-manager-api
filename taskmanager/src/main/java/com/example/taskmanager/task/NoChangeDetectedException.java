package com.example.taskmanager.task;

public class NoChangeDetectedException extends RuntimeException {

    public NoChangeDetectedException(String nothingUpdates) {
        super(nothingUpdates);
    }
}
