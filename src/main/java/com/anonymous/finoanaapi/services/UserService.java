package com.anonymous.finoanaapi.services;

import com.anonymous.finoanaapi.models.User;
import com.anonymous.finoanaapi.repositories.UserRepository;
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
}
