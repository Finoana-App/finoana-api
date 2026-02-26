package com.anonymous.finoanaapi.IT;

import static com.anonymous.finoanaapi.config.HttpExceptionAssertion.assertThrowsNotFoundException;
import static com.anonymous.finoanaapi.utils.DummyToken.someToken;
import static com.anonymous.finoanaapi.utils.DummyUser.someUser;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.anonymous.finoanaapi.config.TestConfig;
import com.anonymous.finoanaapi.controllers.api.UsersApi;
import com.anonymous.finoanaapi.controllers.client.ApiException;
import com.anonymous.finoanaapi.controllers.mapper.UserMapper;
import com.anonymous.finoanaapi.controllers.model.UpdateProfileInput;
import com.anonymous.finoanaapi.models.User;
import com.anonymous.finoanaapi.utils.exceptions.NotSupportedMapping;
import com.google.firebase.auth.FirebaseAuthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserControllerIT extends TestConfig {
  @Autowired private UserMapper userMapper;
  private User user;
  private String token;

  @BeforeEach
  void setUp() throws FirebaseAuthException {
    user = someUser();
    token = someToken();
    userRegistration.registerWithFirebase(user, token);
  }

  @Test
  void get_current_user_ok() throws ApiException, NotSupportedMapping {
    var usersApi = new UsersApi(anApiClient(token));
    var currentUser = usersApi.getCurrentUser();
    assertEquals(userMapper.toRest(user), currentUser.getUser());
  }

  @Test
  void user_get_own_by_id_ok() throws ApiException, NotSupportedMapping {
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
  void user_update_own_info_ok() throws ApiException, NotSupportedMapping {
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
}
