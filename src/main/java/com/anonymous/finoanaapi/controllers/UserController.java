package com.anonymous.finoanaapi.controllers;

import static com.anonymous.finoanaapi.utils.security.firebase.FirebaseFilter.getPrincipal;

import com.anonymous.finoanaapi.controllers.mapper.UserMapper;
import com.anonymous.finoanaapi.controllers.mapper.UserToUserResponseMapper;
import com.anonymous.finoanaapi.controllers.mapper.UserUpdateDtoToUpdateProfileInputMapper;
import com.anonymous.finoanaapi.controllers.model.UpdateProfileInput;
import com.anonymous.finoanaapi.controllers.model.User;
import com.anonymous.finoanaapi.controllers.model.UserResponse;
import com.anonymous.finoanaapi.services.PrincipalService;
import com.anonymous.finoanaapi.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
  private final UserService userService;
  private final PrincipalService principalService;
  private final UserToUserResponseMapper userToUserResponseMapper;
  private final UserUpdateDtoToUpdateProfileInputMapper userUpdateDtoToUpdateProfileInputMapper;
  private final UserMapper userMapper;

  @GetMapping("/users/me")
  UserResponse getCurrentUser() {
    return userToUserResponseMapper.toRest(principalService.getUser(getPrincipal()));
  }

  @GetMapping("/users/{id}")
  UserResponse getUserById(@PathVariable String id) {
    return userToUserResponseMapper.toRest(userService.getById(id));
  }

  @PutMapping("/users/me")
  User updateCurrentUser(@RequestBody UpdateProfileInput restUser) {
    var user = userUpdateDtoToUpdateProfileInputMapper.toDomain(restUser);
    var save = userService.update(getPrincipal().getId(), user);
    return userMapper.toRest(save);
  }
}
