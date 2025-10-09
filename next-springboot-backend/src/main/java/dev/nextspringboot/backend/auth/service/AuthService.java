package dev.nextspringboot.backend.auth.service;

import dev.nextspringboot.backend.auth.dto.RegisterRequest;
import dev.nextspringboot.backend.auth.dto.UserResponse;
import dev.nextspringboot.backend.auth.entity.UserEntity;
import dev.nextspringboot.backend.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

public UserResponse register(RegisterRequest req) {
  var username = req.getUsername().trim();
  if (username.isBlank()) throw new IllegalArgumentException("username must not be blank");
  if (userRepository.existsByUsername(username)) {
    throw new IllegalStateException("username already exists");
  }
  var user = UserEntity.builder()
      .username(username)
      .password(passwordEncoder.encode(req.getPassword()))
      .build();
  var saved = userRepository.save(user);
  return new UserResponse(saved.getId(), saved.getUsername(), saved.getCreatedAt());
}
}
