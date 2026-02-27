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
    return new UserRegistrationDto(
        rest.getFirebaseToken(),
        rest.getEmail(),
        rest.getFirstName(),
        rest.getLastName(),
        rest.getDisplayName(),
        Optional.ofNullable(rest.getPhotoUrl()),
        Optional.ofNullable(rest.getBio()));
  }
}
