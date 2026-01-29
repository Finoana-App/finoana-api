package com.anonymous.finoanaapi.utils.security.firebase;

import com.anonymous.finoanaapi.models.Principal;
import com.anonymous.finoanaapi.services.UserService;
import com.google.firebase.auth.FirebaseToken;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FirebaseUserToPrincipalMapper implements Function<FirebaseToken, Principal> {
  private final UserService userService;

  @Override
  public Principal apply(FirebaseToken firebaseToken) {
    var correspondingUser = userService.getByEmail(firebaseToken.getEmail());

    return Principal.builder()
        .id(correspondingUser.getId())
        .email(correspondingUser.getEmail())
        .role(correspondingUser.getRole())
        .authServicePictureUrl(firebaseToken.getPicture())
        .username(firebaseToken.getName())
        .appPictureUrl(correspondingUser.getAvatarUrl())
        .build();
  }
}
