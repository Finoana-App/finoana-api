package com.anonymous.finoanaapi.controllers;

import static com.anonymous.finoanaapi.utils.security.firebase.FirebaseFilter.getPrincipal;

import com.anonymous.finoanaapi.controllers.mapper.user.RegisterUserMapper;
import com.anonymous.finoanaapi.controllers.mapper.user.UserMapper;
import com.anonymous.finoanaapi.controllers.mapper.user.UserToUserResponseMapper;
import com.anonymous.finoanaapi.controllers.mapper.user.UserUpdateDtoToUpdateProfileInputMapper;
import com.anonymous.finoanaapi.controllers.model.*;
import com.anonymous.finoanaapi.controllers.validator.RegistrationCurrentUserValidator;
import com.anonymous.finoanaapi.controllers.validator.UserRegistrationDtoValidator;
import com.anonymous.finoanaapi.repositories.dao.UserDao;
import com.anonymous.finoanaapi.repositories.dao.UserDao.Criteria;
import com.anonymous.finoanaapi.services.PrincipalService;
import com.anonymous.finoanaapi.services.UserService;
import jakarta.websocket.server.PathParam;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {
  private final UserService userService;
  private final PrincipalService principalService;
  private final UserToUserResponseMapper userToUserResponseMapper;
  private final UserUpdateDtoToUpdateProfileInputMapper userUpdateDtoToUpdateProfileInputMapper;
  private final UserMapper userMapper;
  private final UserDao userDao;
  private final RegisterUserMapper registerUserMapper;
  private final RegistrationCurrentUserValidator registrationCurrentUserValidator;
  private final UserRegistrationDtoValidator userRegistrationDtoValidator;

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

  @GetMapping("/users/search")
  SearchUsersResponse searchUsers(@PathParam("limit") int limit, @PathParam("q") String q) {
    var users = userDao.findByCriteria(Criteria.builder().name(q).build(), Pageable.ofSize(limit));
    return new SearchUsersResponse()
        .count(users.getSize())
        .users(users.getContent().stream().map(userMapper::toRest).toList());
  }

  @PostMapping("/users/register")
  User registerUser(@RequestBody RegisterInput registerInput) {
    registrationCurrentUserValidator.accept(registerInput);
    var registerInputDomain = registerUserMapper.toDomain(registerInput);
    userRegistrationDtoValidator.accept(registerInputDomain);

    var registerUser = userService.registerUser(List.of(registerInputDomain)).getFirst();
    return userMapper.toRest(registerUser);
  }

  @DeleteMapping("/users/me")
  SuccessResponse deactivateCurrentUser() {
    principalService.disableUser(getPrincipal());
    return getAccountDeactivatedSuccessfullyResponse();
  }

  public static SuccessResponse getAccountDeactivatedSuccessfullyResponse() {
    return new SuccessResponse().message("Account deactivated successfully");
  }
}
