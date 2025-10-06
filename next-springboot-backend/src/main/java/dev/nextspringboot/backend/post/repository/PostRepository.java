package dev.nextspringboot.backend.post.repository;

import dev.nextspringboot.backend.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<PostEntity, UUID> {
    
}
