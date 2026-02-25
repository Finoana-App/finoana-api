package com.anonymous.finoanaapi.config;

import static java.lang.Runtime.getRuntime;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.anonymous.finoanaapi.controllers.client.ApiClient;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class TestConfig {
  @LocalServerPort private int localPort;
  @Autowired protected UserRegistration userRegistration;
  private static final PostgresConfig postgresConfig = new PostgresConfig();

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    postgresConfig.configureProperties(registry);
    registry.add("spring.flyway.locations", () -> "classpath:/db/migration");
  }

  @BeforeAll
  static void setUp() {
    postgresConfig.start();
    getRuntime().addShutdownHook(new Thread(postgresConfig::stop));
    FirebaseConfig.setup();
  }

  protected ApiClient anApiClient(String token) {
    var apiClient = new ApiClient();
    apiClient.setPort(localPort);
    apiClient.setHost("localhost");
    apiClient.setScheme("http");

    if (token != null) {
      apiClient.setRequestInterceptor(
          request -> request.header("Authorization", "Bearer %s".formatted(token)));
    }

    return apiClient;
  }

  protected ApiClient anApiClient() {
    return anApiClient(null);
  }
}
