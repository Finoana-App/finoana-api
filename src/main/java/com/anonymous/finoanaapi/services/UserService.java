package com.anonymous.finoanaapi.services;

import com.anonymous.finoanaapi.models.User;
import com.anonymous.finoanaapi.models.dto.UserRegistrationDto;
import com.anonymous.finoanaapi.models.dto.UserUpdateDto;
import com.anonymous.finoanaapi.models.enums.UserRole;
import com.anonymous.finoanaapi.repositories.UserRepository;
import com.anonymous.finoanaapi.utils.exceptions.NotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  public User save(User user) {
    return saveAll(List.of(user)).getFirst();
  }

  public List<User> registerUser(List<UserRegistrationDto> toSave) {
    return userRepository.saveAll(toSave.stream().map(User::new).toList());
  }

  @Transactional
  public User update(String id, UserUpdateDto dto) {
    var user = getById(id);

    dto.displayName().ifPresent(user::setDisplayName);
    dto.photoUrl().ifPresent(user::setAvatarUrl);
    dto.bio().ifPresent(user::setBio);
    dto.privacyLevel().ifPresent(user::setPrivacyLevel);

    return save(user);
  }

  public User inactivateById(String id) {
    var user = getById(id).inactiveUser();
    return save(user);
  }

  public User activateById(String id) {
    var user = getById(id).activeUser();
    return save(user);
  }

  public User changeRoleById(String id, UserRole role) {
    var user = getById(id);
    user.setRole(role);
    return save(user);
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

  public Page<User> getAll(Pageable pageable) {
    return userRepository.findAll(pageable);
  }
}
