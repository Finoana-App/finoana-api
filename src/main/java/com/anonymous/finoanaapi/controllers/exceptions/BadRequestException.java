package com.anonymous.finoanaapi.controllers.exceptions;

public class BadRequestException extends HttpException {
  public BadRequestException(String message) {
    super(message);
  }
}
