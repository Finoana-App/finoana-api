package com.anonymous.finoanaapi.models.follow;

import static jakarta.persistence.FetchType.LAZY;

import com.anonymous.finoanaapi.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Entity
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_follow")
public class UserFollow {

  @EmbeddedId @EqualsAndHashCode.Include private UserFollowId id;

  @MapsId("followerId")
  @ManyToOne(fetch = LAZY, optional = false)
  @JoinColumn(name = "follower_id", nullable = false, referencedColumnName = "id")
  private User follower;

  @MapsId("followingId")
  @ManyToOne(fetch = LAZY, optional = false)
  @JoinColumn(name = "following_id", nullable = false, referencedColumnName = "id")
  private User following;

  @Builder.Default
  @Column(name = "is_following", nullable = false)
  private Boolean isFollowing = true;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  public UserFollow reactiveFollow() {
    this.isFollowing = true;
    return this;
  }
}
