package com.anonymous.finoanaapi.controllers;

import static com.anonymous.finoanaapi.utils.security.firebase.FirebaseFilter.getPrincipal;

import com.anonymous.finoanaapi.controllers.mapper.user.UserMapper;
import com.anonymous.finoanaapi.controllers.mapper.user.UserToUserResponseMapper;
import com.anonymous.finoanaapi.controllers.mapper.user.UserUpdateDtoToUpdateProfileInputMapper;
import com.anonymous.finoanaapi.controllers.model.SuccessResponse;
import com.anonymous.finoanaapi.controllers.model.UpdateProfileInput;
import com.anonymous.finoanaapi.controllers.model.User;
import com.anonymous.finoanaapi.controllers.model.UserResponse;
import com.anonymous.finoanaapi.services.PrincipalService;
import com.anonymous.finoanaapi.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users/me")
public class PrincipalController {
  private final UserService userService;
  private final PrincipalService principalService;
  private final UserToUserResponseMapper userToUserResponseMapper;
  private final UserUpdateDtoToUpdateProfileInputMapper userUpdateDtoToUpdateProfileInputMapper;
  private final UserMapper userMapper;

  @GetMapping
  UserResponse getCurrentUser() {
    return userToUserResponseMapper.toRest(principalService.getUser(getPrincipal()));
  }

  @PutMapping
  User updateCurrentUser(@RequestBody UpdateProfileInput restUser) {
    var user = userUpdateDtoToUpdateProfileInputMapper.toDomain(restUser);
    var save = userService.update(getPrincipal().getId(), user);
    return userMapper.toRest(save);
  }

  @DeleteMapping
  SuccessResponse deactivateCurrentUser() {
    principalService.disableUser(getPrincipal());
    return getAccountDeactivatedSuccessfullyResponse();
  }

  public static SuccessResponse getAccountDeactivatedSuccessfullyResponse() {
    return new SuccessResponse().message("Account deactivated successfully");
  }
}
