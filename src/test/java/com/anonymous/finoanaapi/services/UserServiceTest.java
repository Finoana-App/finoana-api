package com.anonymous.finoanaapi.services;

import static com.anonymous.finoanaapi.models.enums.UserRole.COMMON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.anonymous.finoanaapi.config.TestConfig;
import com.anonymous.finoanaapi.models.User;
import java.util.List;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class UserServiceTest extends TestConfig {
  @Autowired private UserService subject;
  private static final Faker faker = new Faker();

  @Test
  void find_all_user_ok() {
    var users = subject.findAll();
    assertNotNull(users);
  }

  @Test
  void save_new_common_user_ok() {
    var user = someCommonUser();

    var saved = subject.saveAll(List.of(user));

    assertEquals(1, saved.size());
    assertEquals(user, saved.getFirst());
  }

  private static User someCommonUser() {
    return User.builder()
        .email(faker.internet().emailAddress())
        .firstName(faker.name().firstName())
        .lastName(faker.name().lastName())
        .displayName(faker.funnyName().name())
        .bio(faker.lorem().paragraph())
        .role(COMMON)
        .build();
  }
}
