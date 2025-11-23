package com.anonymous.finoanaapi.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class PostgresConfig {
  private final PostgreSQLContainer container =
      new PostgreSQLContainer(DockerImageName.parse("postgres:17-alpine"));

  public void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.username", container::getUsername);
    registry.add("spring.datasource.password", container::getPassword);
  }

  public void start() {
    container.start();
  }

  public void stop() {
    container.stop();
  }
}
