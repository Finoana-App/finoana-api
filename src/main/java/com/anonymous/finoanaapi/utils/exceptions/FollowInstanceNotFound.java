package com.anonymous.finoanaapi.utils.exceptions;

import com.anonymous.finoanaapi.models.follow.UserFollowId;

public class FollowInstanceNotFound extends Exception {
  public FollowInstanceNotFound(String followerId, String followingId) {
    super("Follow instance with id [%s, $s] not found".formatted(followerId, followingId));
  }

  public FollowInstanceNotFound(UserFollowId id) {
    this(id.getFollowerId(), id.getFollowingId());
  }
}
