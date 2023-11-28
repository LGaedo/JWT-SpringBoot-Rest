package com.example.bciusermicroservice.exception;

import com.example.bciusermicroservice.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UsernameNotFoundException e) {
        ErrorResponse.ErrorDetail errorDetail = new ErrorResponse.ErrorDetail(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );

        ErrorResponse errorResponse = new ErrorResponse(Arrays.asList(errorDetail));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleBadRequestException(RuntimeException e) {
        ErrorResponse.ErrorDetail errorDetail = new ErrorResponse.ErrorDetail(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );

        ErrorResponse errorResponse = new ErrorResponse(Collections.singletonList(errorDetail));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(SignUpException.class)
    public ResponseEntity<?> handleSignUpException(SignUpException e) {
        ErrorResponse.ErrorDetail errorDetail = new ErrorResponse.ErrorDetail(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );

        ErrorResponse errorResponse = new ErrorResponse(Collections.singletonList(errorDetail));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        ErrorResponse.ErrorDetail errorDetail = new ErrorResponse.ErrorDetail(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage()
        );

        ErrorResponse errorResponse = new ErrorResponse(Arrays.asList(errorDetail));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleException(ResponseStatusException e) {
        ErrorResponse.ErrorDetail errorDetail = new ErrorResponse.ErrorDetail(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );

        ErrorResponse errorResponse = new ErrorResponse(Arrays.asList(errorDetail));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}


