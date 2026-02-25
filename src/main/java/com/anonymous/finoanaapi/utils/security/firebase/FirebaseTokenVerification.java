package com.anonymous.finoanaapi.utils.security.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FirebaseTokenVerification {
  private final FirebaseAuth firebaseAuth;

  public FirebaseToken verifyIdToken(String token) throws FirebaseAuthException {
    return firebaseAuth.verifyIdToken(token);
  }
}
