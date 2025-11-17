package com.anonymous.finoanaapi.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {
  @Autowired UserService subject;

  @Test
  void find_all_user_ok() {
    var users = subject.findAll();
    assertNotNull(users);
  }
}
