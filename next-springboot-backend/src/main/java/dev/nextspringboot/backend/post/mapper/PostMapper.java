package dev.nextspringboot.backend.post.mapper;

import org.springframework.stereotype.Component;

import dev.nextspringboot.backend.post.dto.PostRequest;
import dev.nextspringboot.backend.post.dto.PostResponse;
import dev.nextspringboot.backend.post.entity.PostEntity;

@Component
public final class PostMapper {
  public PostResponse toResponse(PostEntity postEntity) {
    return PostResponse.builder()
        .id(postEntity.getId())
        .title(postEntity.getTitle())
        .body(postEntity.getBody())
        .createdAt(postEntity.getCreatedAt())
        .build();
  }

  public void applyRequestToEntity(PostRequest postRequest, PostEntity postEntity) {
    postEntity.setTitle(postRequest.getTitle());
    postEntity.setBody(postRequest.getBody());
  }
}
