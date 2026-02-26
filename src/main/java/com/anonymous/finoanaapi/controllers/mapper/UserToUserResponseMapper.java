package com.anonymous.finoanaapi.controllers.mapper;

import com.anonymous.finoanaapi.controllers.model.UserResponse;
import com.anonymous.finoanaapi.models.User;
import com.anonymous.finoanaapi.utils.exceptions.NotSupportedMapping;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserToUserResponseMapper extends AbstractDomaineToRestMapper<User, UserResponse> {
  private final UserMapper userMapper;

  @Override
  public UserResponse toRest(User domain) throws NotSupportedMapping {
    return new UserResponse().user(userMapper.toRest(domain));
  }
}
