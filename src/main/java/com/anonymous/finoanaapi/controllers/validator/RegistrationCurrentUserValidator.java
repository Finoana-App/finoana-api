package com.anonymous.finoanaapi.controllers.validator;

import com.anonymous.finoanaapi.controllers.exceptions.BadRequestException;
import com.anonymous.finoanaapi.controllers.model.RegisterInput;
import com.anonymous.finoanaapi.utils.security.firebase.FirebaseTokenVerification;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegistrationCurrentUserValidator implements Consumer<RegisterInput> {
  private final FirebaseTokenVerification tokenVerification;

  @Override
  @SneakyThrows
  public void accept(RegisterInput toSave) {
    var token = toSave.getFirebaseToken();

    if (token == null || token.isBlank()) {
      throw new BadRequestException("No token provided");
    }

    var firebaseToken = tokenVerification.verifyIdToken(token);
    var firebaseTokenEmail = firebaseToken.getEmail();

    if (!firebaseTokenEmail.equals(toSave.getEmail())) {
      throw new BadRequestException(
          "The requested user email to be created %s doesn't match the owner of the FireBase token provided. The email must be %s"
              .formatted(toSave.getEmail(), firebaseTokenEmail));
    }
  }
}
