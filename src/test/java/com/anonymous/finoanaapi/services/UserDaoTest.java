package com.anonymous.finoanaapi.services;

import static com.anonymous.finoanaapi.services.utils.DummyUser.someCommonUser;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.anonymous.finoanaapi.config.TestConfig;
import com.anonymous.finoanaapi.models.User;
import com.anonymous.finoanaapi.repositories.UserRepository;
import com.anonymous.finoanaapi.repositories.dao.UserDao;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class UserDaoTest extends TestConfig {
  @Autowired private UserDao subject;
  @Autowired private UserRepository userRepository;

  @Test
  void find_user_by_criteria_ok() {
    var userWithCommonName = userWithCommonName();
    var userWithStrangeFirstName = userWithStrangeFirstName();
    var userWithStrangeLastName = userWithStrangeLastName();
      var users = userRepository.saveAll(
            List.of(userWithCommonName, userWithStrangeLastName, userWithStrangeFirstName));

    var noFilter = subject.findByCriteria(null, Pageable.ofSize(10));
    var filterByName = subject.findByCriteria("strange", Pageable.ofSize(10));

    assertTrue(noFilter.getContent().containsAll(users));
    assertTrue(filterByName.getContent().contains(userWithStrangeFirstName));
    assertTrue(filterByName.getContent().contains(userWithStrangeLastName));
  }

  private User userWithStrangeLastName() {
    var userWithStrangeLastName = someCommonUser();
    userWithStrangeLastName.setLastName("strange name");
    return userWithStrangeLastName;
  }

  private User userWithStrangeFirstName() {
    var userWithStrangeFirstName = someCommonUser();
    userWithStrangeFirstName.setFirstName("strange name");
    return userWithStrangeFirstName;
  }

  private User userWithCommonName() {
    var userWithCommonName = someCommonUser();
    userWithCommonName.setFirstName("common");
    userWithCommonName.setLastName("common");
    return userWithCommonName;
  }
}
