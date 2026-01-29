package com.anonymous.finoanaapi.utils.exceptions;

import static com.anonymous.finoanaapi.utils.security.SecurityConf.AUTHORIZATION_HEADER;
import static com.anonymous.finoanaapi.utils.security.firebase.FirebaseFilter.BEARER_PREFIX;

public class BearerNotFound extends Exception {
  public BearerNotFound() {
    super("Header %s not start with %s".formatted(AUTHORIZATION_HEADER, BEARER_PREFIX));
  }
}
