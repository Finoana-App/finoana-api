package com.anonymous.finoanaapi.IT;

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

  // TODO: self follow test case
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

  // TODO: unfollow not followed user exception
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
}
