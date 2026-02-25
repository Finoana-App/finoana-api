package com.anonymous.finoanaapi.controllers.exceptions;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import com.anonymous.finoanaapi.controllers.client.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InternalToExternalExceptionHandler {
  @ExceptionHandler(value = {ForbiddenException.class})
  ResponseEntity<ApiException> handleForbidden(ForbiddenException exception) {
    return new ResponseEntity<>(toRest(exception, FORBIDDEN), FORBIDDEN);
  }

  private static ApiException toRest(Exception e, HttpStatus status) {
    return new ApiException(status.value(), e.getMessage());
  }
}
