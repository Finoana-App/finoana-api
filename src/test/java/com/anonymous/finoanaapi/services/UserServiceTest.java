package com.anonymous.finoanaapi.services;

import static com.anonymous.finoanaapi.services.utils.DummyUser.someCommonUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.anonymous.finoanaapi.config.TestConfig;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class UserServiceTest extends TestConfig {
  @Autowired private UserService subject;

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
}
