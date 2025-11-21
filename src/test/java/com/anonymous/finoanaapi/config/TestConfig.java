package com.anonymous.finoanaapi.config;

import static java.lang.Runtime.getRuntime;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class TestConfig {
  private static final PostgresConfig postgresConfig = new PostgresConfig();

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    postgresConfig.configureProperties(registry);
  }

  @BeforeAll
  static void setUp() {
    postgresConfig.start();
    getRuntime().addShutdownHook(new Thread(postgresConfig::stop));
  }
}
