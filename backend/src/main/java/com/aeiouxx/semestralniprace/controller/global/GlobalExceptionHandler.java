package com.aeiouxx.semestralniprace.controller.global;

import com.aeiouxx.semestralniprace.repository.exception.NotFoundException;
import com.aeiouxx.semestralniprace.service.exception.OverlappingActivityEntryException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
            org.hibernate.exception.ConstraintViolationException cve = (org.hibernate.exception.ConstraintViolationException) ex.getCause();
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("{} already exists.".formatted(cve.getConstraintName()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Request could not be processed due to a conflict with the current state of the resource.");
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFound(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<Object> handleUnsupportedOperation(UnsupportedOperationException ex) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body(ex.getMessage());
    }


    @ExceptionHandler(OverlappingActivityEntryException.class)
    public ResponseEntity<Object> handleOverlappingActivityEntry(OverlappingActivityEntryException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }
}
