package com.anonymous.finoanaapi.controllers.mapper.user;

import com.anonymous.finoanaapi.controllers.mapper.AbstractDomaineToRestMapper;
import com.anonymous.finoanaapi.controllers.model.UpdateProfileInput;
import com.anonymous.finoanaapi.models.dto.UserUpdateDto;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserUpdateDtoToUpdateProfileInputMapper
    extends AbstractDomaineToRestMapper<UserUpdateDto, UpdateProfileInput> {
  private final UserPrivacyMapper userPrivacyMapper;

  @Override
  public UserUpdateDto toDomain(UpdateProfileInput rest) {
    return new UserUpdateDto(
        Optional.ofNullable(rest.getDisplayName()),
        Optional.ofNullable(rest.getPhotoUrl()),
        Optional.ofNullable(rest.getBio()),
        Optional.ofNullable(rest.getPrivacyLevel()).map(userPrivacyMapper::toDomain));
  }
}
