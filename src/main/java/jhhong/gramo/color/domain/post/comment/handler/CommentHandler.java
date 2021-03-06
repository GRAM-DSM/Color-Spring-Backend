package jhhong.gramo.color.domain.post.comment.handler;

import jhhong.gramo.color.domain.post.comment.payload.CreateCommentRequest;
import jhhong.gramo.color.domain.post.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@RequiredArgsConstructor
@Component
public class CommentHandler {

    private final CommentService commentService;

    public Mono<ServerResponse> getComment(ServerRequest request) {
        int page = Integer.parseInt(request.queryParam("page")
                .orElse("0"));

        String postId = request.pathVariable("post_id");

        return commentService.getComment(postId, PageRequest.of(page, 6))
                .flatMap(commentListResponse -> ServerResponse.ok().bodyValue(commentListResponse));
    }

    public Mono<ServerResponse> deleteComment(ServerRequest request) {
        String commentId = request.pathVariable("comment_id");

        String postId = request.pathVariable("post_id");

        return ServerResponse.status(204).body(commentService.deleteComment(postId, commentId), Void.class);
    }

    public Mono<ServerResponse> createComment(ServerRequest request) {
        String postId = request.pathVariable("post_id");

        return request.bodyToMono(CreateCommentRequest.class)
                .flatMap(req -> ServerResponse.created(URI.create("/comment"))
                        .body(commentService.createComment(req, postId), Void.class));
    }
}
