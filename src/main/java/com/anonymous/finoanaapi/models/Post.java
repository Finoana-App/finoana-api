package com.anonymous.finoanaapi.models;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static org.hibernate.type.SqlTypes.NAMED_ENUM;

import com.anonymous.finoanaapi.models.enums.PostVisibility;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class Post {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private String id;

  @ManyToOne(fetch = LAZY, optional = false)
  @JoinColumn(name = "author_id", nullable = false, updatable = false)
  private User author;

  @Column(columnDefinition = "TEXT", nullable = false)
  // TODO: must be sanitised, but depend on the usage one the front
  private String content;

  @Builder.Default
  @Enumerated(STRING)
  @JdbcTypeCode(NAMED_ENUM)
  @Column(nullable = false)
  private PostVisibility visibility = PostVisibility.PUBLIC;

  @Builder.Default
  @Column(nullable = false)
  private Boolean anonymous = false;

  @CreationTimestamp
  @EqualsAndHashCode.Exclude
  @Column(name = "created_at", updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  @EqualsAndHashCode.Exclude
  @Column(name = "updated_at")
  private Instant updatedAt;

  @EqualsAndHashCode.Exclude
  @Column(name = "deleted_at")
  private Instant deletedAt;
}
