package dev.nextspringboot.backend.post.service;

import dev.nextspringboot.backend.post.dto.PostRequest;
import dev.nextspringboot.backend.post.dto.PostResponse;
import dev.nextspringboot.backend.post.factory.PostFactory;
import dev.nextspringboot.backend.post.mapper.PostMapper;
import dev.nextspringboot.backend.post.repository.PostRepository;
import dev.nextspringboot.backend.post.entity.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
  private final PostRepository postRepository;
  private final PostMapper postMapper;
  private final PostFactory postFactory;

  public List<PostResponse> getPostResponseList(int offset, int limit) {
    return postRepository.findAll().stream()
        .skip(offset).limit(limit)
        .map(postMapper::toResponse)
        .toList();
  }

  public PostResponse getPostResponse(UUID id) {
    var postEntity = postRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("post not found: " + id));
    return postMapper.toResponse(postEntity);
  }

  public UUID createPost(PostRequest postRequest) {
    validate(postRequest);
    PostEntity postEntity = postFactory.createFrom(postRequest);
    return postRepository.save(postEntity).getId();
  }

  public void updatePost(UUID id, PostRequest postRequest) {
    validate(postRequest);
    PostEntity postEntity = postRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("post not found: " + id));
    postMapper.applyRequestToEntity(postRequest, postEntity);
    postRepository.save(postEntity);
  }

  public void deletePost(UUID id) {
    if (!postRepository.existsById(id)) throw new NoSuchElementException("post not found: " + id);
    postRepository.deleteById(id);
  }

  private void validate(PostRequest postRequest) {
    var title = postRequest.getTitle();
    var body = postRequest.getBody();
    if (title == null || title.isBlank()) throw new IllegalArgumentException("title must not be blank");
    if (title.length() > 200) throw new IllegalArgumentException("title length must be <= 200");
    if (body == null || body.isBlank()) throw new IllegalArgumentException("body must not be blank");
  }
}
