package com.anonymous.finoanaapi.services;

import com.anonymous.finoanaapi.models.Principal;
import com.anonymous.finoanaapi.models.User;
import com.anonymous.finoanaapi.repositories.UserRepository;
import com.anonymous.finoanaapi.utils.exceptions.NotFoundException;
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

  public User disableUser(Principal toDisable) {
    var user = getUser(toDisable);
    user.inactiveUser();
    return userRepository.save(user);
  }
}
