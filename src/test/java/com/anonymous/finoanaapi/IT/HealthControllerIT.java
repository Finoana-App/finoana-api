package com.anonymous.finoanaapi.IT;

import static com.anonymous.finoanaapi.config.HttpExceptionAssertion.assertThrowsForbiddenException;
import static com.anonymous.finoanaapi.controllers.model.PingResponse.MessageEnum.PONG;
import static com.anonymous.finoanaapi.utils.DummyUser.someUser;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.anonymous.finoanaapi.config.TestConfig;
import com.anonymous.finoanaapi.controllers.api.HealthApi;
import org.junit.jupiter.api.Test;

class HealthControllerIT extends TestConfig {
  @Test
  void ping_ok() throws Exception {
    var api = new HealthApi(anApiClient());
    var result = api.ping();
    assertEquals(PONG, result.getMessage());
  }

  @Test
  void secured_ping_ko() {
    var api = new HealthApi(anApiClient());
    assertThrowsForbiddenException(api::securedPing);
  }

  @Test
  void secured_ping_ok() throws Exception {
    var token = "USER";
    var user = userRegistration.registerWithFirebase(someUser(), token);
    var api = new HealthApi(anApiClient(token));

    var result = api.securedPing();
    assertEquals(PONG, result.getMessage());
    userRegistration.removeUserById(user.getId());
  }
}
