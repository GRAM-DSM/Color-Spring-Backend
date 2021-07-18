package jhhong.gramo.color.domain.post.post.handler;

import jhhong.gramo.color.domain.post.entity.enums.Feel;
import jhhong.gramo.color.domain.post.post.payload.CreatePostRequest;
import jhhong.gramo.color.domain.post.post.payload.PostListResponse;
import jhhong.gramo.color.domain.post.post.service.PostService;
import jhhong.gramo.color.global.validator.CustomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@RequiredArgsConstructor
@Component
public class PostHandler {

    private final PostService postService;
    private final CustomValidator customValidator;

    public Mono<ServerResponse> createPost(ServerRequest request) {
        return request.bodyToMono(CreatePostRequest.class)
                .flatMap(customValidator::validate)
                .flatMap(req -> ServerResponse.created(URI.create("/post")).body(postService.createPost(req), Void.class));
    }

    public Mono<ServerResponse> deletePost(ServerRequest request) {
        Mono<Void> result = postService.deletePost(request.pathVariable("postId"));
        return ServerResponse.status(204).body(result, Void.class);
    }

    public Mono<ServerResponse> updatePost(ServerRequest request) {
        String path = request.pathVariable("postId");

        Mono<Void> result = request.bodyToMono(CreatePostRequest.class)
                .flatMap(customValidator::validate)
                .flatMap(req -> postService.updatePost(path, req));

        return ServerResponse.status(204).body(result, Void.class);
    }

    public Mono<ServerResponse> getPost(ServerRequest request) {
        int page = Integer.parseInt(request.queryParam("page")
                .orElse("0"));

        Feel feel = Feel.valueOf(request.queryParam("feel")
                .orElse("ANGRY"));

        Mono<PostListResponse> result = postService.getPost(feel, page);

        return ServerResponse.ok().body(result, PostListResponse.class);
    }

}
