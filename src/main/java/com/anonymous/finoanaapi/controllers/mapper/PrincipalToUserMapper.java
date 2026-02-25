package com.anonymous.finoanaapi.controllers.mapper;

import com.anonymous.finoanaapi.controllers.model.User;
import com.anonymous.finoanaapi.models.Principal;
import com.anonymous.finoanaapi.services.UserService;
import com.anonymous.finoanaapi.utils.exceptions.NotSupportedMapping;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PrincipalToUserMapper extends AbstractDomaineToRestMapper<Principal, User> {
  private final UserService userService;
  private final UserMapper userMapper;

  @Override
  // TODO: move, not a mapper
  public User toRest(Principal domain) throws NotSupportedMapping {
    var currentUser = userService.getByEmail(domain.getEmail());
    return userMapper.toRest(currentUser);
  }
}
