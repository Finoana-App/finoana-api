package com.anonymous.finoanaapi.utils.security.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseInitialisation {

  @PostConstruct
  public void init() throws IOException {
    if (!FirebaseApp.getApps().isEmpty()) {
      return;
    }

    var options =
        FirebaseOptions.builder().setCredentials(GoogleCredentials.getApplicationDefault()).build();

    FirebaseApp.initializeApp(options);
  }

  public static FirebaseToken verifyIdToken(String token) throws FirebaseAuthException {
    return FirebaseAuth.getInstance().verifyIdToken(token);
  }
}
