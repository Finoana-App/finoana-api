package com.anonymous.finoanaapi.controllers.models;

import static java.lang.Integer.parseInt;

import com.anonymous.finoanaapi.controllers.exceptions.BadRequestException;
import lombok.Getter;
import lombok.SneakyThrows;

public class PageSizeParam {
  @Getter private final int value;

  @SneakyThrows(BadRequestException.class)
  public PageSizeParam(String value) {
    int extracted = parseInt(value);

    if (extracted < 0) {
      throw new RuntimeException("Page request value must be >= 0");
    }

    this.value = extracted;
  }
}
