package com.anonymous.finoanaapi.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
  COMMON,
  MANAGER;

  @Override
  public String getAuthority() {
    return this.name();
  }
}
