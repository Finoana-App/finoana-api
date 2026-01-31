package com.anonymous.finoanaapi.IT;

import static com.anonymous.finoanaapi.controllers.model.PingResponse.MessageEnum.PONG;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.anonymous.finoanaapi.config.TestConfig;
import com.anonymous.finoanaapi.controllers.api.HealthApi;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class HealthControllerIT extends TestConfig {

  @Test
  void ping_ok() throws Exception {
    var api = new HealthApi(anApiClient());
    var result = api.ping();
    assertEquals(PONG, result.getMessage());
  }

  @Test
  @Disabled
  void secured_ping_ko() throws Exception {
    var api = new HealthApi(anApiClient());
    var result = api.ping();
    assertEquals(PONG, result.getMessage());
  }
}
