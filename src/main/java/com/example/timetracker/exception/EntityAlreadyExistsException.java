package com.example.timetracker.exception;


public class EntityAlreadyExistsException extends Exception {
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
