package com.anonymous.finoanaapi.IT;

import static com.anonymous.finoanaapi.controllers.model.UserRole.MODERATOR;
import static com.anonymous.finoanaapi.models.enums.UserStatus.ACTIVATED;
import static com.anonymous.finoanaapi.models.enums.UserStatus.INACTIVATED;
import static com.anonymous.finoanaapi.utils.DummyToken.someToken;
import static com.anonymous.finoanaapi.utils.DummyUser.someModerator;
import static com.anonymous.finoanaapi.utils.DummyUser.someUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.anonymous.finoanaapi.config.TestConfig;
import com.anonymous.finoanaapi.controllers.api.AdministrationApi;
import com.anonymous.finoanaapi.controllers.client.ApiException;
import com.anonymous.finoanaapi.controllers.mapper.user.UserMapper;
import com.anonymous.finoanaapi.controllers.model.UpdateRoleInput;
import com.anonymous.finoanaapi.models.User;
import com.anonymous.finoanaapi.models.enums.UserRole;
import com.anonymous.finoanaapi.repositories.UserRepository;
import com.google.firebase.auth.FirebaseAuthException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ModeratorControllerIT extends TestConfig {
  @Autowired private UserMapper userMapper;
  private User moderator;
  private String token;
  @Autowired private UserRepository userRepository;

  @BeforeEach
  void setUp() throws FirebaseAuthException {
    moderator = someModerator();
    token = someToken();
    userRegistration.registerWithFirebase(moderator, token);
  }

  @AfterEach
  void setDown() {
    userRegistration.removeUserById(moderator.getId());
  }

  @Test
  void manager_get_all_user_ok() throws ApiException {
    var administrationApi = new AdministrationApi(anApiClient(token));

    int page = 0;
    int pageSize = 10;
    var users = administrationApi.listAllUsers(page, pageSize);

    assertEquals(pageSize, users.getPageSize());
    assertEquals(page, users.getPage());
    assertFalse(users.getUsers().isEmpty());
  }

  @Test
  void ban_unban_process_ok() throws ApiException, FirebaseAuthException {
    var userToken = someToken();
    var user = userRegistration.registerWithFirebase(someUser(), userToken);
    var moderatorAdministrationApi = new AdministrationApi(anApiClient(token));

    moderatorAdministrationApi.banUser(user.getId());

    var storedUser = userRepository.findById(user.getId());
    assertTrue(storedUser.isPresent());
    assertEquals(INACTIVATED, storedUser.get().getStatus());

    moderatorAdministrationApi.unbanUser(user.getId());

    storedUser = userRepository.findById(user.getId());
    assertTrue(storedUser.isPresent());
    assertEquals(ACTIVATED, storedUser.get().getStatus());
    userRegistration.removeUserById(user.getId());
  }

  @Test
  void change_user_role_to_moderator_ok() throws ApiException, FirebaseAuthException {
    var userToken = someToken();
    var user = userRegistration.registerWithFirebase(someUser(), userToken);
    var moderatorAdministrationApi = new AdministrationApi(anApiClient(token));
    var newModeratorAdministrationApi = new AdministrationApi(anApiClient(userToken));
    int pageSize = 10;
    int page = 0;

    moderatorAdministrationApi.updateUserRole(user.getId(), new UpdateRoleInput().role(MODERATOR));
    var users = newModeratorAdministrationApi.listAllUsers(page, pageSize);

    var storedUser = userRepository.findById(user.getId());
    assertTrue(storedUser.isPresent());
    assertEquals(UserRole.MODERATOR, storedUser.get().getRole());

    assertEquals(pageSize, users.getPageSize());
    assertEquals(page, users.getPage());
    assertFalse(users.getUsers().isEmpty());
    userRegistration.removeUserById(user.getId());
  }
}
