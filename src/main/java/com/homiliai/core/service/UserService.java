package com.homiliai.core.service;

import com.homiliai.core.entity.Parish;
import com.homiliai.core.entity.User;
import com.homiliai.core.repository.ParishRepository;
import com.homiliai.core.repository.UserRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

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

  public String registerUser(User user, Parish parish) {
    // check if a user with the same email already exists
    Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
    if (existingUser.isPresent()) {
      logger.info("User with email {} already exists.", user.getEmail());
      throw new IllegalArgumentException("User with this email already exists.");
    }

    // check if a user with the same username already exists
    Optional<User> existingUser2 = userRepository.findByUsername(user.getUsername());
    if (existingUser2.isPresent()) {
      logger.info("User with username {} already exists.", user.getUsername());
      throw new IllegalArgumentException("User with this username already exists.");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    // if parish details are provided save parish and associate it with the user
    if (parish != null && parish.getName() != null && !parish.getName().isEmpty()) {
      logger.info("Saving parish: {}", parish);
      Parish savedParish = parishRepository.save(parish);
      user.setParish(savedParish);
    }

    logger.info("Saving user: {}", user);
    userRepository.save(user);
    return "User registered successfully";
  }

  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

}
