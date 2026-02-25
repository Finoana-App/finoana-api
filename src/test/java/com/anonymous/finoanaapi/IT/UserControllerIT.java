package com.anonymous.finoanaapi.IT;

import static com.anonymous.finoanaapi.utils.DummyToken.someToken;
import static com.anonymous.finoanaapi.utils.DummyUser.someUser;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.anonymous.finoanaapi.config.TestConfig;
import com.anonymous.finoanaapi.controllers.api.UsersApi;
import com.anonymous.finoanaapi.controllers.client.ApiException;
import com.anonymous.finoanaapi.controllers.mapper.UserMapper;
import com.anonymous.finoanaapi.utils.exceptions.NotSupportedMapping;
import com.google.firebase.auth.FirebaseAuthException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserControllerIT extends TestConfig {
  @Autowired private UserMapper userMapper;

  @Test
  void get_current_user_ok() throws FirebaseAuthException, ApiException, NotSupportedMapping {
    var user = someUser();
    var token = someToken();
    userRegistration.registerWithFirebase(user, token);
    var usersApi = new UsersApi(anApiClient(token));

    var currentUser = usersApi.getCurrentUser();

    assertEquals(userMapper.toRest(user), currentUser.getUser());
  }
}
