package com.anonymous.finoanaapi.controllers.mapper;

import com.anonymous.finoanaapi.controllers.model.UpdateProfileInput;
import com.anonymous.finoanaapi.models.dto.UserUpdateDto;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserUpdateDtoToUpdateProfileInputMapper
    extends AbstractDomaineToRestMapper<UserUpdateDto, UpdateProfileInput> {
  @Override
  public UserUpdateDto toDomain(UpdateProfileInput rest) {
    return new UserUpdateDto(
        Optional.ofNullable(rest.getDisplayName()),
        Optional.ofNullable(rest.getPhotoUrl()),
        Optional.ofNullable(rest.getBio()),
        Optional.ofNullable(rest.getPrivacyLevel()));
  }
}
