package com.aeiouxx.semestralniprace.repository.exception;

public class NotFoundException extends RuntimeException {
    private Class<?> Type;
    public NotFoundException(Class<?> Type) {
        super("Entity of type " + Type.getSimpleName() + " not found.");
        this.Type = Type;
    }
}
