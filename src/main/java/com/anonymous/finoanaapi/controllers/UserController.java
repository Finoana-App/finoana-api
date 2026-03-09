package com.anonymous.finoanaapi.controllers;

import com.anonymous.finoanaapi.controllers.mapper.user.RegisterUserMapper;
import com.anonymous.finoanaapi.controllers.mapper.user.UserListMapper;
import com.anonymous.finoanaapi.controllers.mapper.user.UserMapper;
import com.anonymous.finoanaapi.controllers.model.*;
import com.anonymous.finoanaapi.controllers.models.PageParam;
import com.anonymous.finoanaapi.controllers.models.PageSizeParam;
import com.anonymous.finoanaapi.controllers.utils.PageParamsToPageable;
import com.anonymous.finoanaapi.controllers.validator.RegistrationCurrentUserValidator;
import com.anonymous.finoanaapi.controllers.validator.UserRegistrationDtoValidator;
import com.anonymous.finoanaapi.repositories.dao.UserDao;
import com.anonymous.finoanaapi.repositories.dao.UserDao.Criteria;
import com.anonymous.finoanaapi.services.UserService;
import jakarta.websocket.server.PathParam;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
  private final UserService userService;
  private final UserMapper userMapper;
  private final UserDao userDao;
  private final RegisterUserMapper registerUserMapper;
  private final RegistrationCurrentUserValidator registrationCurrentUserValidator;
  private final UserRegistrationDtoValidator userRegistrationDtoValidator;
  private final UserListMapper userListMapper;
  private final PageParamsToPageable pageMapper;

  @GetMapping("/search")
  UsersListResponse searchUsers(
      @RequestParam(value = "page", defaultValue = "1") PageParam page,
      @RequestParam(value = "page_size", defaultValue = "20") PageSizeParam pageSize,
      @PathParam("name") String name) {
    var users =
        userDao.findByCriteria(
            Criteria.builder().name(name).build(), pageMapper.apply(page, pageSize));
    return userListMapper.toRest(users);
  }

  @PostMapping("/register")
  User registerUser(@RequestBody RegisterInput registerInput) {
    registrationCurrentUserValidator.accept(registerInput);
    var registerInputDomain = registerUserMapper.toDomain(registerInput);
    userRegistrationDtoValidator.accept(registerInputDomain);

    var registerUser = userService.registerUser(List.of(registerInputDomain)).getFirst();
    return userMapper.toRest(registerUser);
  }

  @GetMapping
  UsersListResponse listAllUsers(
      @RequestParam(value = "page", defaultValue = "1") PageParam pageNumber,
      @RequestParam(value = "page_size", defaultValue = "20") PageSizeParam pageSize) {
    var result = userService.getAll(pageMapper.apply(pageNumber, pageSize));
    return userListMapper.toRest(result);
  }
}
