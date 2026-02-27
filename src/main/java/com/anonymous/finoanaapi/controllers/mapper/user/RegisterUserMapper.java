package com.anonymous.finoanaapi.controllers.mapper.user;

import com.anonymous.finoanaapi.controllers.mapper.AbstractDomaineToRestMapper;
import com.anonymous.finoanaapi.controllers.model.RegisterInput;
import com.anonymous.finoanaapi.models.dto.UserRegistrationDto;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserMapper
    extends AbstractDomaineToRestMapper<UserRegistrationDto, RegisterInput> {
  @Override
  public UserRegistrationDto toDomain(RegisterInput rest) {
    return toDomain(null, rest);
  }

  public UserRegistrationDto toDomain(String id, RegisterInput rest) {
    return new UserRegistrationDto(
        id,
        rest.getEmail(),
        rest.getDisplayName(),
        Optional.ofNullable(rest.getBio()),
        Optional.ofNullable(rest.getBio()));
  }
}
