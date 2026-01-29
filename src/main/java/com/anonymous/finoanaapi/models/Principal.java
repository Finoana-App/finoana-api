package com.anonymous.finoanaapi.models;

import com.anonymous.finoanaapi.models.enums.UserRole;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Builder
@AllArgsConstructor
public class Principal implements UserDetails {
  private final String id;
  private final String email;
  private final String username;
  private final UserRole role;
  private final String appPictureUrl;
  private final String authServicePictureUrl;

  @Override
  public Collection<UserRole> getAuthorities() {
    return List.of(role);
  }

  @Override
  public String getPassword() {
    throw new RuntimeException("Password not store in the application");
  }

  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return UserDetails.super.isEnabled();
  }
}
