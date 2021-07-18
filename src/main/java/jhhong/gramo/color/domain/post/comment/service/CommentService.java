package jhhong.gramo.color.domain.post.comment.service;

import jhhong.gramo.color.domain.post.comment.payload.CommentContentResponse;
import jhhong.gramo.color.domain.post.comment.payload.CommentListResponse;
import jhhong.gramo.color.domain.post.comment.payload.CreateCommentRequest;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface CommentService {

    Mono<Void> createComment(CreateCommentRequest request, String postId);

    Mono<Void> deleteComment(String postId, String commentId);

    Mono<CommentListResponse> getComment(String postId, Pageable pageable);

}
