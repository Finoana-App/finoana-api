package com.anonymous.finoanaapi.IT;

import static com.anonymous.finoanaapi.config.FirebaseConfig.setupFirebaseAuthUser;
import static com.anonymous.finoanaapi.config.HttpExceptionAssertion.assertThrowsBadRequestException;
import static com.anonymous.finoanaapi.config.HttpExceptionAssertion.assertThrowsForbiddenException;
import static com.anonymous.finoanaapi.config.HttpExceptionAssertion.assertThrowsNotFoundException;
import static com.anonymous.finoanaapi.controllers.UserController.getAccountDeactivatedSuccessfullyResponse;
import static com.anonymous.finoanaapi.controllers.model.UserRole.MODERATOR;
import static com.anonymous.finoanaapi.utils.DummyToken.someToken;
import static com.anonymous.finoanaapi.utils.DummyUser.someUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.anonymous.finoanaapi.config.TestConfig;
import com.anonymous.finoanaapi.controllers.api.AdministrationApi;
import com.anonymous.finoanaapi.controllers.api.UsersApi;
import com.anonymous.finoanaapi.controllers.client.ApiException;
import com.anonymous.finoanaapi.controllers.mapper.user.UserMapper;
import com.anonymous.finoanaapi.controllers.model.RegisterInput;
import com.anonymous.finoanaapi.controllers.model.UpdateProfileInput;
import com.anonymous.finoanaapi.controllers.model.UpdateRoleInput;
import com.anonymous.finoanaapi.models.User;
import com.anonymous.finoanaapi.models.enums.UserRole;
import com.anonymous.finoanaapi.repositories.UserRepository;
import com.google.firebase.auth.FirebaseAuthException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserControllerIT extends TestConfig {
  @Autowired private UserMapper userMapper;
  private User user;
  private String token;
  @Autowired private UserRepository userRepository;

  @BeforeEach
  void setUp() throws FirebaseAuthException {
    user = someUser();
    token = someToken();
    userRegistration.registerWithFirebase(user, token);
  }

  @AfterEach
  void setDown() {
    userRegistration.removeUserById(user.getId());
  }

  @Test
  void get_current_user_ok() throws ApiException {
    var usersApi = new UsersApi(anApiClient(token));
    var currentUser = usersApi.getCurrentUser();
    assertEquals(userMapper.toRest(user), currentUser.getUser());
  }

  @Test
  void user_get_own_by_id_ok() throws ApiException {
    var usersApi = new UsersApi(anApiClient(token));
    var currentUser = usersApi.getUserById(user.getId());
    assertEquals(userMapper.toRest(user), currentUser.getUser());
  }

  @Test
  void get_not_existing_user_by_id_ko() {
    var usersApi = new UsersApi(anApiClient(token));
    assertThrowsNotFoundException(() -> usersApi.getUserById("user-that-doesn't-exist*-id"));
  }

  @Test
  void user_update_own_info_ok() throws ApiException {
    var newBio = "hello";
    var usersApi = new UsersApi(anApiClient(token));
    var actualUserInfo = usersApi.updateCurrentUser(new UpdateProfileInput().bio(newBio));

    var previousUserInfo = userMapper.toRest(user);
    assertEquals(previousUserInfo.getId(), actualUserInfo.getId());
    assertEquals(previousUserInfo.getEmail(), actualUserInfo.getEmail());
    assertEquals(newBio, actualUserInfo.getBio());
    assertEquals(previousUserInfo.getCreatedAt(), actualUserInfo.getCreatedAt());
    assertEquals(previousUserInfo.getIsVerified(), actualUserInfo.getIsVerified());
    assertEquals(previousUserInfo.getDisplayName(), actualUserInfo.getDisplayName());
    assertEquals(previousUserInfo.getLastSeenAt(), actualUserInfo.getLastSeenAt());
    assertEquals(previousUserInfo.getPhotoUrl(), actualUserInfo.getPhotoUrl());
    assertEquals(previousUserInfo.getPrivacyLevel(), actualUserInfo.getPrivacyLevel());
    assertEquals(previousUserInfo.getRole(), actualUserInfo.getRole());
  }

  @Test
  void filer_user_by_criteria_ok() throws ApiException {
    var domainUser = userRepository.save(someUser());
    var restUser = userMapper.toRest(domainUser);
    var usersApi = new UsersApi(anApiClient(token));

    var searchUsers = usersApi.searchUsers(domainUser.getFirstName(), 10);

    assertTrue(searchUsers.getUsers().contains(restUser));
  }

  @Test
  void user_login_process_ok() throws FirebaseAuthException, ApiException {
    var token = someToken();
    var user = someUser();
    setupFirebaseAuthUser(token, user.getEmail(), user.getFirstName(), user.getAvatarUrl());

    var usersApi = new UsersApi(anApiClient());

    // TODO: test without token
    var registerInput =
        new RegisterInput()
            .firebaseToken(token)
            .bio(user.getBio())
            .displayName(user.getDisplayName())
            .email(user.getEmail())
            .photoUrl(user.getAvatarUrl())
            .firstName(user.getFirstName())
            .lastName(user.getLastName());

    var registered = usersApi.registerUser(registerInput);
    var registeredId = registered.getId();

    registered.setId(null);
    registered.setCreatedAt(null);
    assertEquals(userMapper.toRest(user), registered);

    userRepository.deleteById(registeredId);
  }

  @Test
  void user_login_process_without_information_ko() throws FirebaseAuthException, ApiException {
    var token = someToken();
    var user = someUser();
    setupFirebaseAuthUser(token, user.getEmail(), user.getFirstName(), user.getAvatarUrl());

    var usersApi = new UsersApi(anApiClient());

    var requestWithoutToken =
        new RegisterInput()
            .bio(user.getBio())
            .displayName(user.getDisplayName())
            .email(user.getEmail())
            .photoUrl(user.getAvatarUrl())
            .firstName(user.getFirstName())
            .lastName(user.getLastName());
    var requestWithoutOtherInfo = new RegisterInput().email(user.getEmail()).firebaseToken(token);
    var requestWithWrongEmail =
        new RegisterInput()
            .firebaseToken(token)
            .bio(user.getBio())
            .displayName(user.getDisplayName())
            .email("test" + user.getEmail())
            .photoUrl(user.getAvatarUrl())
            .firstName(user.getFirstName())
            .lastName(user.getLastName());

    assertThrowsBadRequestException(
        () -> usersApi.registerUser(requestWithoutToken),
        e -> assertTrue(e.getMessage().contains("No token provided")));
    assertThrowsBadRequestException(
        () -> usersApi.registerUser(requestWithoutOtherInfo),
        e -> {
          assertTrue(e.getMessage().contains("No last name provided"));
          assertTrue(e.getMessage().contains("No first provided"));
          assertTrue(e.getMessage().contains("No display name provided"));
        });
    assertThrowsBadRequestException(
        () -> usersApi.registerUser(requestWithWrongEmail),
        e ->
            assertTrue(
                e.getMessage()
                    .contains(
                        " doesn't match the owner of the FireBase token provided. The email must be %s"
                            .formatted(user.getEmail()))));
  }

  @Test
  void inactivate_current_user_ok() throws ApiException {
    var usersApi = new UsersApi(anApiClient(token));
    var successResponse = usersApi.deactivateCurrentUser();
    assertEquals(getAccountDeactivatedSuccessfullyResponse(), successResponse);
  }

  @Test
  void inactivate_current_user_twice_not_ko() throws ApiException {
    var usersApi = new UsersApi(anApiClient(token));
    var successResponse = usersApi.deactivateCurrentUser();
    assertEquals(getAccountDeactivatedSuccessfullyResponse(), successResponse);

    assertThrowsBadRequestException(() -> usersApi.deactivateCurrentUser());
  }

  @Test
  void user_get_all_user_ko() {
    var administrationApi = new AdministrationApi(anApiClient(token));

    assertThrowsForbiddenException(() -> administrationApi.listAllUsers(1, 1));
  }

  @Test
  void user_change_user_role_to_moderator_ko() throws FirebaseAuthException {
    var anotherUserWithoutId = someUser();
    var anotherUserToken = someToken();
    var anotherUser = userRegistration.registerWithFirebase(anotherUserWithoutId, anotherUserToken);
    var userAdministrationApi = new AdministrationApi(anApiClient(token));
    var anotherUserAdministrationApi = new AdministrationApi(anApiClient(anotherUserToken));

    assertThrowsForbiddenException(
        () ->
            userAdministrationApi.updateUserRole(
                anotherUser.getId(), new UpdateRoleInput().role(MODERATOR)));

    var storedUser = userRepository.findById(anotherUser.getId());
    assertTrue(storedUser.isPresent());
    assertEquals(UserRole.USER, storedUser.get().getRole());
    assertThrowsForbiddenException(() -> anotherUserAdministrationApi.listAllUsers(1, 1));
    userRegistration.removeUserById(anotherUser.getId());
  }
}
