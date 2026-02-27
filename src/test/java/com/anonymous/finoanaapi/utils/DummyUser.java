package com.anonymous.finoanaapi.utils;

import static com.anonymous.finoanaapi.models.enums.UserRole.COMMON;

import com.anonymous.finoanaapi.models.User;
import net.datafaker.Faker;

public class DummyUser {
  private static final Faker faker = new Faker();

  public static User someUser() {
    return User.builder()
        .email(faker.internet().emailAddress())
        .firstName(faker.name().firstName())
        .lastName(faker.name().lastName())
        .avatarUrl(faker.internet().url())
        .role(COMMON)
        .displayName(faker.funnyName().name())
        .bio(faker.lorem().paragraph(5))
        .build();
  }
}
