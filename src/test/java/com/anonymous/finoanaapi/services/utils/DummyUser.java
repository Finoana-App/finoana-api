package com.anonymous.finoanaapi.services.utils;

import static com.anonymous.finoanaapi.models.enums.UserRole.COMMON;

import com.anonymous.finoanaapi.models.User;
import java.util.List;
import java.util.stream.IntStream;
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
        .role(COMMON)
        .build();
  }

  public static List<User> someCommonUsers(int count) {
    return IntStream.of(count).mapToObj(i -> someCommonUser()).toList();
  }
}
