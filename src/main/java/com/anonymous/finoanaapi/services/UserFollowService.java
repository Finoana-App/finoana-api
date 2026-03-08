package com.anonymous.finoanaapi.services;

import com.anonymous.finoanaapi.models.User;
import com.anonymous.finoanaapi.models.follow.UserFollow;
import com.anonymous.finoanaapi.models.follow.UserFollowId;
import com.anonymous.finoanaapi.repositories.UserFollowRepository;
import com.anonymous.finoanaapi.utils.exceptions.FollowInstanceNotFound;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserFollowService {
  private final UserFollowRepository userFollowRepository;

  public Optional<UserFollow> findById(UserFollowId id) {
    return userFollowRepository.findById(id);
  }

  public UserFollow makeFollow(UserFollowId id) {
    var followById = findById(id);

    var toSave =
        followById
            .map(UserFollow::reactiveFollow)
            .orElse(
                UserFollow.builder()
                    .id(id)
                    .follower(User.builder().id(id.getFollowerId()).build())
                    .following(User.builder().id(id.getFollowingId()).build())
                    .build());

    return userFollowRepository.save(toSave);
  }

  public UserFollow disableFollow(UserFollowId id) throws FollowInstanceNotFound {
    var follow = findById(id).orElseThrow(() -> new FollowInstanceNotFound(id));
    follow.setIsFollowing(false);
    return userFollowRepository.save(follow);
  }
}
