package com.anonymous.finoanaapi.controllers;

import static com.anonymous.finoanaapi.utils.security.firebase.FirebaseFilter.getPrincipal;

import com.anonymous.finoanaapi.controllers.mapper.PrincipalToUserMapper;
import com.anonymous.finoanaapi.controllers.model.UserResponse;
import com.anonymous.finoanaapi.utils.exceptions.NotSupportedMapping;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
  private final PrincipalToUserMapper principalToUserMapper;

  @GetMapping("/users/me")
  UserResponse getCurrentUser() throws NotSupportedMapping {
    var currentUser = principalToUserMapper.toRest(getPrincipal());
    return new UserResponse().user(currentUser);
  }
}
