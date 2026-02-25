package com.anonymous.finoanaapi.controllers.mapper;

import static com.anonymous.finoanaapi.controllers.model.User.PrivacyLevelEnum.PUBLIC;

import com.anonymous.finoanaapi.models.User;
import com.anonymous.finoanaapi.utils.exceptions.NotSupportedMapping;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper
    extends AbstractDomaineToRestMapper<User, com.anonymous.finoanaapi.controllers.model.User> {
  private final UserRoleMapper userRoleMapper;

  @Override
  public com.anonymous.finoanaapi.controllers.model.User toRest(User domain)
      throws NotSupportedMapping {
    return new com.anonymous.finoanaapi.controllers.model.User()
        .id(domain.getId())
        .bio(domain.getBio())
        .createdAt(domain.getCreatedAt())
        .displayName(domain.getDisplayName())
        .email(domain.getEmail())
        .isVerified(domain.getEmailVerified())
        .lastSeenAt(domain.getLastLogin())
        .photoUrl(domain.getAvatarUrl())
        .privacyLevel(PUBLIC)
        .role(userRoleMapper.toRest(domain.getRole()));
  }
}
