package com.anonymous.finoanaapi.IT;

import static com.anonymous.finoanaapi.config.HttpExceptionAssertion.assertThrowsBadRequestException;
import static com.anonymous.finoanaapi.config.HttpExceptionAssertion.assertThrowsNotFoundException;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.anonymous.finoanaapi.config.TestConfig;
import com.anonymous.finoanaapi.config.UserListSetup;
import com.anonymous.finoanaapi.controllers.api.UsersApi;
import com.anonymous.finoanaapi.controllers.client.ApiException;
import com.anonymous.finoanaapi.controllers.mapper.user.UserMapper;
import com.anonymous.finoanaapi.models.follow.UserFollowId;
import com.anonymous.finoanaapi.repositories.UserFollowRepository;
import com.google.firebase.auth.FirebaseAuthException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserFollowControllerIT extends TestConfig {
  @Autowired private UserListSetup usersSetup;
  @Autowired private UserMapper userMapper;
  @Autowired private UserFollowRepository userFollowRepository;

  @BeforeEach
  void setup() throws FirebaseAuthException {
    usersSetup.setup(2, userRegistration);
  }

  @AfterEach
  void shutDown() {
    usersSetup.shutdown(userRegistration);
  }

  @Test
  void user_follow_another_user_ok() throws ApiException {
    var axelSetup = usersSetup.get(0);
    var barbaraSetup = usersSetup.get(1);
    var alexUsersApi = new UsersApi(anApiClient(axelSetup.getToken()));

    var followedUser = alexUsersApi.addFollow(barbaraSetup.getUser().getId());

    assertEquals(userMapper.toRest(barbaraSetup.getUser()), followedUser);
    userFollowRepository.deleteById(
        new UserFollowId(axelSetup.getUser().getId(), barbaraSetup.getUser().getId()));
  }

  @Test
  void user_self_follow_ko() {
    var axelSetup = usersSetup.get(0);
    var alexUsersApi = new UsersApi(anApiClient(axelSetup.getToken()));
    assertThrowsBadRequestException(() -> alexUsersApi.addFollow(axelSetup.getUser().getId()));
  }

  @Test
  void user_follow_another_user_then_unfollow_ok() throws ApiException {
    var axelSetup = usersSetup.get(0);
    var barbaraSetup = usersSetup.get(1);
    var alexUsersApi = new UsersApi(anApiClient(axelSetup.getToken()));

    var followedUser = alexUsersApi.addFollow(barbaraSetup.getUser().getId());

    assertEquals(userMapper.toRest(barbaraSetup.getUser()), followedUser);

    var unfollowedUser = alexUsersApi.deleteFollow(barbaraSetup.getUser().getId());

    assertEquals(followedUser, unfollowedUser);
    userFollowRepository.deleteById(
        new UserFollowId(axelSetup.getUser().getId(), barbaraSetup.getUser().getId()));
  }

  @Test
  void user_unfollow_not_followed_user_ko() {
    var axelSetup = usersSetup.get(0);
    var barbaraSetup = usersSetup.get(1);
    var alexUsersApi = new UsersApi(anApiClient(axelSetup.getToken()));
    assertThrowsNotFoundException(() -> alexUsersApi.deleteFollow(barbaraSetup.getUser().getId()));
  }
}
