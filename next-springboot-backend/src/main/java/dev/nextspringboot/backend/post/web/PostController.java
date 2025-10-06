package dev.nextspringboot.backend.post.web;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.nextspringboot.backend.post.dto.PostRequest;
import lombok.RequiredArgsConstructor;

import dev.nextspringboot.backend.post.service.PostService;
import dev.nextspringboot.backend.post.dto.PostResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Validated
public class PostController {
    private final PostService postService;

    // DTOの配列を返す
    @GetMapping
    public List<PostResponse> postResponseList(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {
        return postService.getPostResponseList(offset, limit);
    }

    // DTOを1件取得
    @GetMapping("/{id}")
    public PostResponse getPostResponse(@PathVariable UUID id) {
        return postService.getPostResponse(id);
    }

    // 作成: 201を返す
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse createPost(@Valid @RequestBody PostRequest postRequest) {
        UUID id = postService.createPost(postRequest);
        return postService.getPostResponse(id);
    }
    
    // 更新: 200で更新後DTOを返す
    @PutMapping("/{id}")
    public PostResponse updatePost(@PathVariable UUID id, @Valid @RequestBody PostRequest postRequest) {
        postService.updatePost(id, postRequest);
        return postService.getPostResponse(id);
    }

    // 削除: 204を返す
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
    }

}
