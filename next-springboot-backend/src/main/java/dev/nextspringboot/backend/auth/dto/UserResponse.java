package dev.nextspringboot.backend.auth.dto;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
  private UUID id;
  private String username;
  private Instant createdAt;
}
