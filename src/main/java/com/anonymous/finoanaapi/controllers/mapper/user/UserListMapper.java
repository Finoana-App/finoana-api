package com.anonymous.finoanaapi.controllers.mapper.user;

import com.anonymous.finoanaapi.controllers.mapper.AbstractDomaineToRestMapper;
import com.anonymous.finoanaapi.controllers.model.UsersListResponse;
import com.anonymous.finoanaapi.models.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserListMapper extends AbstractDomaineToRestMapper<Page<User>, UsersListResponse> {
  private final UserMapper userMapper;

  @Override
  public UsersListResponse toRest(Page<User> domain) {
    return new UsersListResponse()
        .users(domain.getContent().stream().map(userMapper::toRest).toList())
        .limit(domain.getSize())
        .page(domain.getNumber())
        .total(domain.getTotalElements());
  }
}
