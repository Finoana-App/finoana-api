package com.anonymous.finoanaapi.config;

import static com.anonymous.finoanaapi.config.FirebaseConfig.setupFirebaseAuthUser;

import com.anonymous.finoanaapi.models.User;
import com.anonymous.finoanaapi.repositories.UserRepository;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserRegistration {
  private final UserRepository userRepository;

  public User register(User user) {
    return userRepository.save(user);
  }

  public User registerWithFirebase(User user, String token) throws FirebaseAuthException {
    setupFirebaseAuthUser(token, user.getEmail(), "name", user.getAvatarUrl());
    return register(user);
  }
}
