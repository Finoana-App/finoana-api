package com.anonymous.finoanaapi.controllers.mapper;

import static com.anonymous.finoanaapi.controllers.model.User.RoleEnum.MODERATOR;
import static com.anonymous.finoanaapi.controllers.model.User.RoleEnum.USER;

import com.anonymous.finoanaapi.controllers.model.User.RoleEnum;
import com.anonymous.finoanaapi.models.enums.UserRole;
import com.anonymous.finoanaapi.utils.exceptions.NotSupportedMapping;
import org.springframework.stereotype.Component;

@Component
public class UserRoleMapper extends AbstractDomaineToRestMapper<UserRole, RoleEnum> {
  @Override
  // TODO: remove unnecessary exception
  public RoleEnum toRest(UserRole domain) throws NotSupportedMapping {
    // TODO: need to be renamed
    return switch (domain) {
      case COMMON -> USER;
      case MANAGER -> MODERATOR;
    };
  }
}
