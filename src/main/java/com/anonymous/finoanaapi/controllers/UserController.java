package com.anonymous.finoanaapi.controllers;

import static com.anonymous.finoanaapi.utils.security.firebase.FirebaseFilter.getPrincipal;

import com.anonymous.finoanaapi.controllers.mapper.user.RegisterUserMapper;
import com.anonymous.finoanaapi.controllers.mapper.user.UserListMapper;
import com.anonymous.finoanaapi.controllers.mapper.user.UserMapper;
import com.anonymous.finoanaapi.controllers.mapper.user.UserRoleMapper;
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
import org.springframework.data.domain.PageRequest;
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
  private final UserListMapper userListMapper;
  private final UserRoleMapper userRoleMapper;

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

  @GetMapping("/users")
  UsersListResponse listAllUsers(
      @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
      @RequestParam(value = "limit", defaultValue = "20") Integer pageLimit) {
    var result = userService.getAll(PageRequest.of(pageNumber, pageLimit));
    return userListMapper.toRest(result);
  }

  @PostMapping("/users/{id}/ban")
  SuccessResponse banUser(@PathVariable String id) {
    userService.inactivateById(id);
    return getAccountSuccessfullyBaned();
  }

  @PostMapping("/users/{id}/unban")
  SuccessResponse unbanUser(@PathVariable String id) {
    userService.activateById(id);
    return getAccountSuccessfullyUnbaned();
  }

  @PutMapping("/users/{id}/role")
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

  public static SuccessResponse getAccountDeactivatedSuccessfullyResponse() {
    return new SuccessResponse().message("Account deactivated successfully");
  }
}
