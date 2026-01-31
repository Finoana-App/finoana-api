package com.anonymous.finoanaapi.IT;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.anonymous.finoanaapi.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class HealthControllerIT extends TestConfig {
  @Autowired private MockMvc mockMvc;

  @Test
  void ping() throws Exception {
    mockMvc
        .perform(get("/health/ping"))
        .andExpect(status().isOk())
        .andExpect(content().string("pong"));
  }
}
