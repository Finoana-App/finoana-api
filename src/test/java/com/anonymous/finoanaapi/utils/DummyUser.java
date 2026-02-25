package com.anonymous.finoanaapi.utils;

import static com.anonymous.finoanaapi.models.enums.UserRole.COMMON;

import com.anonymous.finoanaapi.models.User;

public class DummyUser {
  public static User someUser() {
    return User.builder()
        .email("test." + Math.round(Math.random() * 10_000) + "@gmail.com")
        .firstName("test")
        .lastName("test")
        .avatarUrl("url")
        .role(COMMON)
        .displayName("test")
        .build();
  }
}
