package dev.nextspringboot.backend.post.factory;

import java.time.Instant;
import org.springframework.stereotype.Component;
import dev.nextspringboot.backend.post.dto.PostRequest;
import dev.nextspringboot.backend.post.entity.PostEntity;

@Component
public class PostFactory {
    public PostEntity createFrom(PostRequest postRequest) {
        return PostEntity.builder()
            .title(postRequest.getTitle().trim())
            .body(postRequest.getBody().trim())
            .createdAt(Instant.now())
            .build();
    }
}
