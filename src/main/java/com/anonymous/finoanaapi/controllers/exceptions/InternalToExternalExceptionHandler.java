package com.anonymous.finoanaapi.controllers.exceptions;

import static org.springframework.http.HttpStatus.*;

import com.anonymous.finoanaapi.controllers.client.ApiException;
import com.anonymous.finoanaapi.utils.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InternalToExternalExceptionHandler {
  @ExceptionHandler(value = ForbiddenException.class)
  ResponseEntity<ApiException> handleForbidden(ForbiddenException exception) {
    return toRestResponse(exception, FORBIDDEN);
  }

  @ExceptionHandler(value = NotFoundException.class)
  ResponseEntity<ApiException> handleNotFound(NotFoundException exception) {
    return toRestResponse(exception, NOT_FOUND);
  }

  private static ResponseEntity toRestResponse(Exception exception, HttpStatus status) {
    return new ResponseEntity<>(toRest(exception, status), status);
  }

  private static ApiException toRest(Exception e, HttpStatus status) {
    return new ApiException(status.value(), e.getMessage());
  }
}
