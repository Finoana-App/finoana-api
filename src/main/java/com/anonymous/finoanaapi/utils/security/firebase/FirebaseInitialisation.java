package com.anonymous.finoanaapi.utils.security.firebase;

import static com.google.auth.oauth2.GoogleCredentials.getApplicationDefault;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FirebaseInitialisation {
  @Bean
  public FirebaseAuth getFirebaseAuth() {
    if (!FirebaseApp.getApps().isEmpty()) {
      return FirebaseAuth.getInstance();
    }

    try {
      var options = FirebaseOptions.builder().setCredentials(getApplicationDefault()).build();

      FirebaseApp.initializeApp(options);
    } catch (IOException e) {
      throw new RuntimeException("Firebase credential not loading, " + e);
    }

    return FirebaseAuth.getInstance();
  }
}
