package com.isbd.exception;

public class EntityNotSavedException extends RuntimeException {
    public EntityNotSavedException(String message) {
        super(message);
    }
}
