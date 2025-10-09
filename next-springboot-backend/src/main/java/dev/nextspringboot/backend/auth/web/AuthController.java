package dev.nextspringboot.backend.auth.web;

import dev.nextspringboot.backend.auth.dto.RegisterRequest;
import dev.nextspringboot.backend.auth.dto.UserResponse;
import dev.nextspringboot.backend.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponse register(@Valid @RequestBody RegisterRequest req) {
    return authService.register(req);
  }
}
