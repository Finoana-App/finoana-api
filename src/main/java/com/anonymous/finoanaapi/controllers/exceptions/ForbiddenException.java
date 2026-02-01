package com.anonymous.finoanaapi.controllers.exceptions;

public class ForbiddenException extends HttpException {
  public ForbiddenException(String message) {
    super(message);
  }
}
