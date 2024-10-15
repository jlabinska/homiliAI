package com.homiliai.core.service;

import com.homiliai.core.entity.Parish;
import com.homiliai.core.entity.User;
import com.homiliai.core.repository.ParishRepository;
import com.homiliai.core.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final ParishRepository parishRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, ParishRepository parishRepository,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.parishRepository = parishRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User registerUser(User user, Parish parish) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    // Jeśli podano szczegóły parafii, zapisz parafię i powiąż ją z użytkownikiem
    if (parish != null) {
      Parish savedParish = parishRepository.save(parish);
      user.setParish(savedParish);
    }

    return userRepository.save(user);
  }

  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

}
