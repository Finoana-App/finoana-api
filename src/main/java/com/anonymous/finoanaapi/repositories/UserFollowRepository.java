package com.anonymous.finoanaapi.repositories;

import com.anonymous.finoanaapi.models.follow.UserFollow;
import com.anonymous.finoanaapi.models.follow.UserFollowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFollowRepository extends JpaRepository<UserFollow, UserFollowId> {
  int countByFollowingId(String followingId);

  int countByFollowerId(String followerId);
}
