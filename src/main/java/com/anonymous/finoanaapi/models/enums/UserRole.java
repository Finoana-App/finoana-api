package com.anonymous.finoanaapi.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
  USER,
  MODERATOR,
  ADMIN;

  @Override
  public String getAuthority() {
    return "ROLE_%s".formatted(this.name());
  }

  public String getRole() {
    return name();
  }
}
