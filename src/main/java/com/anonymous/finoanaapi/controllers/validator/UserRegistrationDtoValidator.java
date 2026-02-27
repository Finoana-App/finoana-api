package com.anonymous.finoanaapi.controllers.validator;

import static java.util.stream.Collectors.*;

import com.anonymous.finoanaapi.controllers.exceptions.BadRequestException;
import com.anonymous.finoanaapi.models.dto.UserRegistrationDto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationDtoValidator implements Consumer<UserRegistrationDto> {
  @Override
  public void accept(UserRegistrationDto registrationDto) {
    var issues = new ArrayList<String>();

    if (registrationDto.email() == null) {
      issues.add("No email provided");
    }
    if (registrationDto.lastName() == null) {
      issues.add("No last name provided");
    }
    if (registrationDto.firstName() == null) {
      issues.add("No first provided");
    }
    if (registrationDto.displayName() == null) {
      issues.add("No display name provided");
    }
    if (registrationDto.firebaseToken() == null) {
      issues.add("No firebase token provided");
    }

    if (!issues.isEmpty()) {
      throw new BadRequestException(formatIssuesMessage(issues));
    }
  }

  private static @NonNull String formatIssuesMessage(Collection<String> issues) {
    return "Validation failed:\n"
        + issues.stream().map(error -> "- %s".formatted(error)).collect(joining("\n"));
  }
}
