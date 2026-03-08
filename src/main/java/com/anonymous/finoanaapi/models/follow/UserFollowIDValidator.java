package com.anonymous.finoanaapi.models.follow;

import com.anonymous.finoanaapi.controllers.exceptions.BadRequestException;
import java.util.function.Consumer;
import org.springframework.stereotype.Component;

@Component
public class UserFollowIDValidator implements Consumer<UserFollowId> {
  @Override
  public void accept(UserFollowId id) {
    if (id.getFollowerId() == null) {
      throw new BadRequestException("Follower id null");
    }
    if (id.getFollowingId() == null) {
      throw new BadRequestException("Following id null");
    }
    if (id.getFollowerId().equals(id.getFollowingId())) {
      throw new BadRequestException("Follower id and following id are the same");
    }
  }
}
