package com.modyo.app.controller.advice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.modyo.app.exception.PokemonServiceException;
import com.modyo.app.models.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ModyoExceptionHandler {

  @ResponseStatus(NOT_FOUND)
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(Exception ex) {
    ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            ex.getMessage(),
            HttpStatus.NOT_FOUND
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(PokemonServiceException.class)
  public ResponseEntity<ErrorResponse> handleErrorInternalException(Exception ex) {
    log.warn(" exception occurred while processing the request: {}.", ex.getMessage(), ex);
    ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}
