package com.anonymous.finoanaapi.controllers;

import com.anonymous.finoanaapi.controllers.mapper.user.RegisterUserMapper;
import com.anonymous.finoanaapi.controllers.mapper.user.UserListMapper;
import com.anonymous.finoanaapi.controllers.mapper.user.UserMapper;
import com.anonymous.finoanaapi.controllers.model.*;
import com.anonymous.finoanaapi.controllers.validator.RegistrationCurrentUserValidator;
import com.anonymous.finoanaapi.controllers.validator.UserRegistrationDtoValidator;
import com.anonymous.finoanaapi.repositories.dao.UserDao;
import com.anonymous.finoanaapi.repositories.dao.UserDao.Criteria;
import com.anonymous.finoanaapi.services.UserService;
import jakarta.websocket.server.PathParam;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
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

  @GetMapping("/search")
  SearchUsersResponse searchUsers(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "page_size", defaultValue = "20") int pageSize,
      @PathParam("name") String name) {
    var users =
        userDao.findByCriteria(Criteria.builder().name(name).build(), PageRequest.of(page, pageSize));
    return new SearchUsersResponse()
        .count(users.getSize())
        .users(users.getContent().stream().map(userMapper::toRest).toList());
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
      @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
      @RequestParam(value = "page_size", defaultValue = "20") Integer pageSize) {
    var result = userService.getAll(PageRequest.of(pageNumber, pageSize));
    return userListMapper.toRest(result);
  }
}
