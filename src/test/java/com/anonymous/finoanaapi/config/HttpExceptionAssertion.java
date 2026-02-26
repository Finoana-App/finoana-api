package com.anonymous.finoanaapi.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.anonymous.finoanaapi.controllers.client.ApiException;
import java.util.function.Consumer;
import org.junit.jupiter.api.function.Executable;
import org.springframework.http.HttpStatus;

public class HttpExceptionAssertion {
  public static void assertThrowsForbiddenException(Executable executable) {
    assertThrowsForbiddenException(executable, e -> {});
  }

  public static void assertThrowsForbiddenException(
      Executable executable, Consumer<ApiException> assertExceptionContent) {
    assertException(
        executable,
        e -> {
          assertEquals(HttpStatus.FORBIDDEN.value(), e.getCode());
          assertExceptionContent.accept(e);
        });
  }

  public static void assertThrowsNotFoundException(Executable executable) {
    assertThrowsNotFoundException(executable, e -> {});
  }

  public static void assertThrowsNotFoundException(
      Executable executable, Consumer<ApiException> assertExceptionContent) {
    assertException(
        executable,
        e -> {
          assertEquals(HttpStatus.NOT_FOUND.value(), e.getCode());
          assertExceptionContent.accept(e);
        });
  }

  public static void assertException(
      Executable executable, Consumer<ApiException> assertExceptionContent) {
    var exception = assertThrows(ApiException.class, executable);
    assertExceptionContent.accept(exception);
  }
}
