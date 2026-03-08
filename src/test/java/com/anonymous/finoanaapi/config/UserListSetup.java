package com.anonymous.finoanaapi.config;

import com.google.firebase.auth.FirebaseAuthException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserListSetup {
  private final List<UserSetup> usersSetup = new ArrayList<>();

  public List<UserSetup> setup(int userCount, UserRegistration userRegistration)
      throws FirebaseAuthException {
    for (int i = 0; i <= userCount; i++) {
      var userSetup = new UserSetup();
      userSetup.setup(userRegistration);
      usersSetup.add(userSetup);
    }
    return usersSetup;
  }

  /** I beg you to clear all the object linked to the user before shut it down here */
  public void shutdown(UserRegistration userRegistration) {
    for (UserSetup userSetup : usersSetup) {
      userSetup.shutdown(userRegistration);
    }
    usersSetup.clear();
  }

  public UserSetup get(int index) {
    return usersSetup.get(index);
  }
}
