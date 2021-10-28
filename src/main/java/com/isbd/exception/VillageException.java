package com.isbd.exception;

import org.springframework.stereotype.Component;

@Component
public class VillageException {
    public static class EntityNotFoundException extends RuntimeException {
        public EntityNotFoundException(String message) {
            super(message);
        }
    }
}
