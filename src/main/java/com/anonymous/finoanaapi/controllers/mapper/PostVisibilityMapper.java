package com.anonymous.finoanaapi.controllers.mapper;

import static com.anonymous.finoanaapi.controllers.model.PostVisibility.FOLLOWERS_ONLY;
import static com.anonymous.finoanaapi.controllers.model.PostVisibility.PRIVATE;
import static com.anonymous.finoanaapi.controllers.model.PostVisibility.PUBLIC;

import com.anonymous.finoanaapi.models.enums.PostVisibility;
import org.springframework.stereotype.Component;

@Component
public class PostVisibilityMapper
    extends AbstractDomaineToRestMapper<
        PostVisibility, com.anonymous.finoanaapi.controllers.model.PostVisibility> {
  @Override
  public PostVisibility toDomain(com.anonymous.finoanaapi.controllers.model.PostVisibility rest) {
    return switch (rest) {
      case PUBLIC -> PostVisibility.PUBLIC;
      case PRIVATE -> PostVisibility.PRIVATE;
      case FOLLOWERS_ONLY -> PostVisibility.FOLLOWERS_ONLY;
    };
  }

  @Override
  public com.anonymous.finoanaapi.controllers.model.PostVisibility toRest(PostVisibility domain) {
    return switch (domain) {
      case PUBLIC -> PUBLIC;
      case PRIVATE -> PRIVATE;
      case FOLLOWERS_ONLY -> FOLLOWERS_ONLY;
    };
  }
}
