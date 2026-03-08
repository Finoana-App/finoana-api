package com.anonymous.finoanaapi.services;

import com.anonymous.finoanaapi.models.User;
import com.anonymous.finoanaapi.models.follow.UserFollow;
import com.anonymous.finoanaapi.models.follow.UserFollowId;
import com.anonymous.finoanaapi.repositories.UserFollowRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserFollowService {
  private final UserFollowRepository userFollowRepository;

  public UserFollow makeFollow(String followerId, String followingId) {
    var ids = UserFollowId.builder().followerId(followerId).followingId(followingId).build();
    var followById = userFollowRepository.findById(ids);

    var toSave =
        followById
            .map(UserFollow::reactiveFollow)
            .orElse(
                UserFollow.builder()
                    .id(ids)
                    .follower(User.builder().id(followerId).build())
                    .following(User.builder().id(followingId).build())
                    .build());

    return userFollowRepository.save(toSave);
  }
}
