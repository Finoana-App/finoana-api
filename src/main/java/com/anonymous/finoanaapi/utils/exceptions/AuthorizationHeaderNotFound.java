package com.anonymous.finoanaapi.utils.exceptions;

import static com.anonymous.finoanaapi.utils.security.SecurityConf.AUTHORIZATION_HEADER;

public class AuthorizationHeaderNotFound extends Exception {
  public AuthorizationHeaderNotFound() {
    super("Authorization header %s not found".formatted(AUTHORIZATION_HEADER));
  }
}
