package com.anonymous.finoanaapi.controllers.mapper.user;

import static com.anonymous.finoanaapi.controllers.model.UserRole.ADMIN;
import static com.anonymous.finoanaapi.controllers.model.UserRole.MODERATOR;
import static com.anonymous.finoanaapi.controllers.model.UserRole.USER;

import com.anonymous.finoanaapi.controllers.mapper.AbstractDomaineToRestMapper;
import com.anonymous.finoanaapi.models.enums.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserRoleMapper
    extends AbstractDomaineToRestMapper<
        UserRole, com.anonymous.finoanaapi.controllers.model.UserRole> {
  @Override
  public UserRole toDomain(com.anonymous.finoanaapi.controllers.model.UserRole rest) {
    return switch (rest) {
      case USER -> UserRole.USER;
      case MODERATOR -> UserRole.MODERATOR;
      case ADMIN -> UserRole.ADMIN;
    };
  }

  @Override
  public com.anonymous.finoanaapi.controllers.model.UserRole toRest(UserRole domain) {
    return switch (domain) {
      case USER -> USER;
      case MODERATOR -> MODERATOR;
      case ADMIN -> ADMIN;
    };
  }
}
