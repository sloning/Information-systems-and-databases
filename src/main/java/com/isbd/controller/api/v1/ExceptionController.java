package com.isbd.controller.api.v1;

import com.isbd.dto.ErrorResponse;
import com.isbd.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.Objects;

@ControllerAdvice
public class ExceptionController {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(400, 1, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotSavedException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotSavedException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(400, 2, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(WrongCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleWrongCredentialsException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(401, 3, ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleJwtAuthenticationException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(401, 4, ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEntityAlreadyExistsException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(400, 5, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(KitHaveBeenAlreadyGivenException.class)
    public ResponseEntity<ErrorResponse> handleKitHaveBeenAlreadyGivenException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(400, 6, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintValidationException(ConstraintViolationException ex) {
        return new ResponseEntity<>(new ErrorResponse(400, 7,
                Objects.requireNonNull(ex.getConstraintViolations().iterator().next().getMessage())),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(new ErrorResponse(400, 8,
                Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
