package com.anonymous.finoanaapi.controllers.mapper.user;

import com.anonymous.finoanaapi.controllers.mapper.AbstractDomaineToRestMapper;
import com.anonymous.finoanaapi.controllers.model.UserResponse;
import com.anonymous.finoanaapi.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserToUserResponseMapper extends AbstractDomaineToRestMapper<User, UserResponse> {
  private final UserMapper userMapper;

  @Override
  public UserResponse toRest(User domain) {
    return new UserResponse().user(userMapper.toRest(domain));
  }
}
