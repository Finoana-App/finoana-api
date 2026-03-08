package com.anonymous.finoanaapi.models.follow;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserFollowId implements Serializable {

  @Column(name = "follower_id", nullable = false)
  private String followerId;

  @Column(name = "following_id", nullable = false)
  private String followingId;
}
