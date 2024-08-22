package com.challenge.discountcalculator.infrastructure.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebExceptionHandler {
    @ExceptionHandler(value = { ResourceNotFoundException.class, EntityNotFoundException.class })
    public ResponseEntity<ProblemDetail> handleBadRequestException() {
        ProblemDetail body = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Resource that you requested was not found");
        return ResponseEntity.of(body).build();
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public ResponseEntity<ProblemDetail> handleIllegalArgumentException(IllegalArgumentException e) {
        ProblemDetail body = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.of(body).build();
    }
}

