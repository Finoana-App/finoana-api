package com.anonymous.finoanaapi.controllers.mapper;

import static com.anonymous.finoanaapi.controllers.model.User.RoleEnum.MODERATOR;
import static com.anonymous.finoanaapi.controllers.model.User.RoleEnum.USER;

import com.anonymous.finoanaapi.controllers.model.User.RoleEnum;
import com.anonymous.finoanaapi.models.enums.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserRoleMapper extends AbstractDomaineToRestMapper<UserRole, RoleEnum> {
  @Override
  public RoleEnum toRest(UserRole domain) {
    // TODO: need to be renamed
    return switch (domain) {
      case COMMON -> USER;
      case MANAGER -> MODERATOR;
    };
  }
}
