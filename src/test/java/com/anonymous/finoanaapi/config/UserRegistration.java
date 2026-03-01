package com.anonymous.finoanaapi.config;

import static com.anonymous.finoanaapi.config.FirebaseConfig.setupFirebaseAuthUser;

import com.anonymous.finoanaapi.models.User;
import com.anonymous.finoanaapi.repositories.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class UserRegistration {
  private final UserRepository userRepository;

  public User register(User user) {
    return userRepository.save(user);
  }

  public User registerWithFirebase(User user, String token) throws FirebaseAuthException {
    setupFirebaseAuthUser(token, user.getEmail(), "name", user.getAvatarUrl());
    var registrationLog =
        "Mock token-email auth: %s => %s"
            .formatted(token, FirebaseAuth.getInstance().verifyIdToken(token).getEmail());
    log.info(registrationLog);
    return register(user);
  }

  public void removeUserById(String id) {
    userRepository.deleteById(id);
  }
}
