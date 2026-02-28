package com.anonymous.finoanaapi.utils;

import static com.anonymous.finoanaapi.models.enums.UserRole.MODERATOR;
import static com.anonymous.finoanaapi.models.enums.UserRole.USER;

import com.anonymous.finoanaapi.models.User;
import com.anonymous.finoanaapi.models.enums.UserRole;
import net.datafaker.Faker;

public class DummyUser {
  private static final Faker faker = new Faker();

  public static User someUser() {
    return someUser(USER);
  }

  public static User someModerator() {
    return someUser(MODERATOR);
  }

  public static User someUser(UserRole role) {
    return User.builder()
        .email(faker.internet().emailAddress())
        .firstName(faker.name().firstName())
        .lastName(faker.name().lastName())
        .avatarUrl(faker.internet().url())
        .role(role)
        .displayName(faker.funnyName().name())
        .bio(faker.lorem().paragraph(5))
        .build();
  }
}
