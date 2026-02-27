package com.anonymous.finoanaapi.services;

import com.anonymous.finoanaapi.models.Principal;
import com.anonymous.finoanaapi.models.User;
import com.anonymous.finoanaapi.models.dto.UserRegistrationDto;
import com.anonymous.finoanaapi.repositories.UserRepository;
import com.anonymous.finoanaapi.utils.exceptions.NotFoundException;
import com.anonymous.finoanaapi.utils.exceptions.RegistrationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PrincipalService {
  private final UserRepository userRepository;

  public User getUser(Principal principal) {
    return userRepository
        .findById(principal.getId())
        .orElseThrow(
            () -> new NotFoundException("User associate with principal: %s".formatted(principal)));
  }

  public User registerUser(UserRegistrationDto toSave, Principal currentUser)
      throws RegistrationException {
    if (!currentUser.getEmail().equals(toSave.email())) {
      throw new RegistrationException(
          "Registered email %s for the token of the user %s is different for the user to save %s"
              .formatted(currentUser.getEmail(), currentUser, toSave.email()));
    }
    if (!currentUser.getId().equals(toSave.id())) {
      throw new RegistrationException(
          "Registered id %s for the token of the user %s is different for the user to save %s"
              .formatted(currentUser.getId(), currentUser, toSave.id()));
    }
    return userRepository.save(new User(toSave));
  }
}
