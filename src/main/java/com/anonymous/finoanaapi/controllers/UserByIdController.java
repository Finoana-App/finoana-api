package com.anonymous.finoanaapi.controllers;

import com.anonymous.finoanaapi.controllers.mapper.user.UserMapper;
import com.anonymous.finoanaapi.controllers.mapper.user.UserRoleMapper;
import com.anonymous.finoanaapi.controllers.mapper.user.UserToUserResponseMapper;
import com.anonymous.finoanaapi.controllers.model.SuccessResponse;
import com.anonymous.finoanaapi.controllers.model.UpdateRoleInput;
import com.anonymous.finoanaapi.controllers.model.User;
import com.anonymous.finoanaapi.controllers.model.UserResponse;
import com.anonymous.finoanaapi.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users/{id}")
public class UserByIdController {
  private final UserService userService;
  private final UserToUserResponseMapper userToUserResponseMapper;
  private final UserMapper userMapper;
  private final UserRoleMapper userRoleMapper;

  @GetMapping
  UserResponse getUserById(@PathVariable String id) {
    return userToUserResponseMapper.toRest(userService.getById(id));
  }

  @PostMapping("/ban")
  SuccessResponse banUser(@PathVariable String id) {
    userService.inactivateById(id);
    return getAccountSuccessfullyBaned();
  }

  @PostMapping("/unban")
  SuccessResponse unbanUser(@PathVariable String id) {
    userService.activateById(id);
    return getAccountSuccessfullyUnbaned();
  }

  @PutMapping("/role")
  User updateUserRole(@PathVariable String id, @RequestBody UpdateRoleInput updateRoleInput) {
    var user = userService.changeRoleById(id, userRoleMapper.toDomain(updateRoleInput.getRole()));
    return userMapper.toRest(user);
  }

  private static SuccessResponse getAccountSuccessfullyBaned() {
    return new SuccessResponse().message("Account successfully baned");
  }

  private static SuccessResponse getAccountSuccessfullyUnbaned() {
    return new SuccessResponse().message("Account successfully unbaned");
  }
}
