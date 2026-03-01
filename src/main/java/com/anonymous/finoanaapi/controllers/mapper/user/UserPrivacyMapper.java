package com.anonymous.finoanaapi.controllers.mapper.user;

import static com.anonymous.finoanaapi.controllers.model.PrivacyLevelEnum.ANONYMOUS;
import static com.anonymous.finoanaapi.controllers.model.PrivacyLevelEnum.PRIVATE;
import static com.anonymous.finoanaapi.controllers.model.PrivacyLevelEnum.PUBLIC;

import com.anonymous.finoanaapi.controllers.mapper.AbstractDomaineToRestMapper;
import com.anonymous.finoanaapi.controllers.model.PrivacyLevelEnum;
import com.anonymous.finoanaapi.models.enums.PrivacyLevel;
import org.springframework.stereotype.Component;

@Component
public class UserPrivacyMapper extends AbstractDomaineToRestMapper<PrivacyLevel, PrivacyLevelEnum> {
  @Override
  public PrivacyLevel toDomain(PrivacyLevelEnum rest) {
    return switch (rest) {
      case PUBLIC -> PrivacyLevel.PUBLIC;
      case PRIVATE -> PrivacyLevel.PRIVATE;
      case ANONYMOUS -> PrivacyLevel.ANONYMOUS;
    };
  }

  @Override
  public PrivacyLevelEnum toRest(PrivacyLevel domain) {
    return switch (domain) {
      case PUBLIC -> PUBLIC;
      case PRIVATE -> PRIVATE;
      case ANONYMOUS -> ANONYMOUS;
    };
  }
}
