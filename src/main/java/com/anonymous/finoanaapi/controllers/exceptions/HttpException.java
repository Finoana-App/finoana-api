package com.anonymous.finoanaapi.controllers.exceptions;

public class HttpException extends RuntimeException {
  public HttpException(String message) {
    super(message);
  }
}
