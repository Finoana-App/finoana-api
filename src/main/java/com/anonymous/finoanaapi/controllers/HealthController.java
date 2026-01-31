package com.anonymous.finoanaapi.controllers;

import static com.anonymous.finoanaapi.controllers.model.PingResponse.MessageEnum.PONG;

import com.anonymous.finoanaapi.controllers.model.PingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {
  @GetMapping("/ping")
  PingResponse ping() {
    return new PingResponse().message(PONG);
  }

  @GetMapping("/secured/ping")
  String securedPing() {
    return "pong";
  }
}
