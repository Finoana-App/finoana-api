package com.anonymous.finoanaapi.services.utils;

import static com.anonymous.finoanaapi.models.enums.UserRole.USER;

import com.anonymous.finoanaapi.models.User;
import net.datafaker.Faker;

public class DummyUser {
  private static final Faker faker = new Faker();

  public static User someCommonUser() {
    return User.builder()
        .email(faker.internet().emailAddress())
        .firstName(faker.name().firstName())
        .lastName(faker.name().lastName())
        .displayName(faker.funnyName().name())
        .bio(faker.lorem().paragraph())
        .role(USER)
        .build();
  }
}
