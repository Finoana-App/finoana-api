package com.anonymous.finoanaapi.models;

import static com.anonymous.finoanaapi.models.enums.UserStatus.ACTIVE;
import static jakarta.persistence.GenerationType.IDENTITY;

import com.anonymous.finoanaapi.models.enums.UserRole;
import com.anonymous.finoanaapi.models.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"")
public class User {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private String id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "display_name")
  private String displayName;

  @Column private String bio;

  @Column(name = "avatar_url")
  private String avatarUrl;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserRole role;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserStatus status = ACTIVE;

  @Column(name = "is_anonymous_by_default", nullable = false)
  private Boolean isAnonymousByDefault;

  @Column(name = "email_verified")
  private Boolean emailVerified;

  @CreationTimestamp
  @Column(name = "created_at")
  private Instant createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private Instant updatedAt;

  @Column(name = "last_login")
  private Instant lastLogin;
}
