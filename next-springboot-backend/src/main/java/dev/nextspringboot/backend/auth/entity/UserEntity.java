package dev.nextspringboot.backend.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class UserEntity {
  @Id @Column(nullable = false, updatable = false)
  private UUID id;

  @Column(nullable = false, length = 100)
  private String username;

  @Column(nullable = false)
  private String password; // bcrypt

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @PrePersist void onCreate() {
    if (id == null) id = UUID.randomUUID();
    if (createdAt == null) createdAt = Instant.now();
  }
}
