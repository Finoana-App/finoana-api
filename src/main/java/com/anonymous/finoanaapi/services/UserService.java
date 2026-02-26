package com.anonymous.finoanaapi.services;

import com.anonymous.finoanaapi.models.User;
import com.anonymous.finoanaapi.repositories.UserRepository;
import com.anonymous.finoanaapi.utils.exceptions.NotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
  private UserRepository userRepository;

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public List<User> saveAll(List<User> users) {
    return userRepository.saveAll(users);
  }

  public User getByEmail(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User with email %s not fount".formatted(email)));
  }

  public User getById(String id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("User with id: %s".formatted(id)));
  }
}
