package com.anonymous.finoanaapi.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.anonymous.finoanaapi.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
class UserServiceTest extends TestConfig {
  @Autowired UserService subject;

  @Test
  void find_all_user_ok() {
    var users = subject.findAll();
    assertNotNull(users);
  }
}
