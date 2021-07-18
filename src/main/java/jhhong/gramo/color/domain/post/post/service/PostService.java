package jhhong.gramo.color.domain.post.post.service;

import jhhong.gramo.color.domain.post.entity.enums.Feel;
import jhhong.gramo.color.domain.post.post.payload.CreatePostRequest;
import jhhong.gramo.color.domain.post.post.payload.PostListResponse;
import reactor.core.publisher.Mono;

public interface PostService {
    Mono<Void> createPost(CreatePostRequest request);

    Mono<Void> updatePost(String id, CreatePostRequest request);

    Mono<Void> deletePost(String id);

    Mono<PostListResponse> getPost(Feel feel, int page);

}
