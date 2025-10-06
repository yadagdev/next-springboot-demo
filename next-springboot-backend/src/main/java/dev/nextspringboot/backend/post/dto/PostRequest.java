package dev.nextspringboot.backend.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
  @NotBlank @Size(max = 200)
  private String title;

  @NotBlank
  private String body;
}
