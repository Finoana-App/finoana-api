package com.anonymous.finoanaapi.models;

import static com.anonymous.finoanaapi.models.enums.UserStatus.ACTIVATED;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static org.hibernate.type.SqlTypes.NAMED_ENUM;

import com.anonymous.finoanaapi.models.enums.UserRole;
import com.anonymous.finoanaapi.models.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
@Table(name = "\"user\"")
public class User {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private String id;

  @Column(nullable = false, unique = true, updatable = false)
  private String email;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "display_name")
  private String displayName;

  @EqualsAndHashCode.Exclude @Column private String bio;

  @EqualsAndHashCode.Exclude
  @Column(name = "avatar_url")
  private String avatarUrl;

  @Enumerated(STRING)
  @Column(nullable = false)
  @JdbcTypeCode(NAMED_ENUM)
  private UserRole role;

  @Builder.Default
  @Enumerated(STRING)
  @Column(nullable = false)
  @JdbcTypeCode(NAMED_ENUM)
  private UserStatus status = ACTIVATED;

  @Builder.Default
  @Column(name = "is_anonymous_by_default", nullable = false)
  private Boolean isAnonymousByDefault = false;

  @Column(name = "email_verified")
  private Boolean emailVerified;

  @CreationTimestamp
  @EqualsAndHashCode.Exclude
  @Column(name = "created_at")
  private Instant createdAt;

  @UpdateTimestamp
  @EqualsAndHashCode.Exclude
  @Column(name = "updated_at")
  private Instant updatedAt;

  @EqualsAndHashCode.Exclude
  @Column(name = "last_login")
  private Instant lastLogin;
}
