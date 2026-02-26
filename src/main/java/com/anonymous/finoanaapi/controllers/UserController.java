package com.anonymous.finoanaapi.controllers;

import static com.anonymous.finoanaapi.utils.security.firebase.FirebaseFilter.getPrincipal;

import com.anonymous.finoanaapi.controllers.mapper.PrincipalToUserMapper;
import com.anonymous.finoanaapi.controllers.mapper.UserToUserResponseMapper;
import com.anonymous.finoanaapi.controllers.model.UserResponse;
import com.anonymous.finoanaapi.services.UserService;
import com.anonymous.finoanaapi.utils.exceptions.NotSupportedMapping;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
  private final UserService userService;
  private final PrincipalToUserMapper principalToUserMapper;
  private final UserToUserResponseMapper userToUserResponseMapper;

  @GetMapping("/users/me")
  UserResponse getCurrentUser() throws NotSupportedMapping {
    var currentUser = principalToUserMapper.toRest(getPrincipal());
    return new UserResponse().user(currentUser);
  }

  @GetMapping("/users/{id}")
  UserResponse getUserById(@PathVariable String id) throws NotSupportedMapping {
    return userToUserResponseMapper.toRest(userService.getById(id));
  }
}
