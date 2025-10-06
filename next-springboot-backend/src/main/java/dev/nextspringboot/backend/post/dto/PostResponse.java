package dev.nextspringboot.backend.post.dto;

import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
  private UUID id;
  private String title;
  private String body;
  private Instant createdAt;
}
