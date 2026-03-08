package com.anonymous.finoanaapi.config;

import static com.anonymous.finoanaapi.utils.DummyToken.someToken;
import static com.anonymous.finoanaapi.utils.DummyUser.someUser;

import com.anonymous.finoanaapi.models.User;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
// TODO: refactor to that if possible
public class UserSetup {
  private User user;
  private String token;

  public void setup(UserRegistration userRegistration) throws FirebaseAuthException {
    token = someToken();
    user = userRegistration.registerWithFirebase(someUser(), token);
  }

  public void shutdown(UserRegistration userRegistration) {
    if (userEmpty()) {
      throw new RuntimeException("User already removed");
    }
    if (tokenEmpty()) {
      throw new RuntimeException("Token already removed");
    }

    userRegistration.removeUserById(user.getId());
    user = null;
    token = null;
  }

  public User getUser() {
    if (userEmpty()) {
      throw new RuntimeException("User not initialized");
    }
    return user;
  }

  private boolean userEmpty() {
    return user == null;
  }

  public String getToken() {
    if (tokenEmpty()) {
      throw new RuntimeException("Token not initialized");
    }
    return token;
  }

  private boolean tokenEmpty() {
    return token == null;
  }
}
