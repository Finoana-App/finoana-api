package com.anonymous.finoanaapi.utils.exceptions;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String information) {
    super("Resource not found, %s".formatted(information));
  }
}
