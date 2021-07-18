package jhhong.gramo.color.domain.post.comment.service;

import jhhong.gramo.color.domain.post.comment.entity.Comment;
import jhhong.gramo.color.domain.post.comment.payload.CommentContentResponse;
import jhhong.gramo.color.domain.post.comment.payload.CommentListResponse;
import jhhong.gramo.color.domain.post.comment.payload.CreateCommentRequest;
import jhhong.gramo.color.domain.post.entity.Post;
import jhhong.gramo.color.domain.post.entity.PostRepository;
import jhhong.gramo.color.domain.post.post.exceptions.InvalidAccessException;
import jhhong.gramo.color.domain.post.post.exceptions.PostNotFoundException;
import jhhong.gramo.color.global.security.authentication.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public Mono<Void> createComment(CreateCommentRequest request, String postId) {
        return postRepository.findById(postId)
                .zipWith(buildComment(request))
                .flatMap(infos -> infos.getT1().addComment(infos.getT2()))
                .flatMap(postRepository::save)
                .then();
    }

    @Override
    public Mono<Void> deleteComment(String postId, String commentId) {
        Mono<Post> postMono = postRepository.findById(postId)
                .switchIfEmpty(Mono.error(PostNotFoundException::new));

        Mono<Post> filteredPostMono = postMono
                .zipWith(authenticationFacade.getUser())
                .filter(postUserTuple -> postUserTuple.getT1().getUserEmail().equals(postUserTuple.getT2().getEmail()))
                .map(Tuple2::getT1)
                .switchIfEmpty(Mono.error(InvalidAccessException::new));

        return filteredPostMono.flatMap(post -> post.deleteComment(commentId))
                .flatMap(postRepository::save)
                .then();
    }

    @Override
    public Mono<CommentListResponse> getComment(String postId, Pageable pageable) {
        return postRepository.findById(postId)
                .flatMap(post -> buildCommentResponse(post, pageable));
    }

    private Mono<Comment> buildComment(CreateCommentRequest request) {
        return authenticationFacade.getUser()
                .map(user ->
                        Comment.builder()
                                .content(request.content())
                                .userEmail(user.getEmail())
                                .userNickname(user.getNickname())
                                .id(UUID.randomUUID().toString())
                                .build()
                );
    }

    private Mono<CommentListResponse> buildCommentResponse(Post post, Pageable pageable) {
        return authenticationFacade.getUser()
                .map(user -> post.getComment()
                        .stream().skip(pageable.getOffset())
                        .limit(pageable.getOffset() + pageable.getPageSize())
                        .map(comment -> CommentContentResponse.builder()
                                .createdAt(comment.getCreatedAt())
                                .isMine(comment.getUserEmail().equals(user.getEmail()))
                                .userNickname(comment.getUserNickname())
                                .content(comment.getContent())
                                .id(comment.getId())
                                .build())
                        .collect(Collectors.toList()))
                .flatMap(commentContentResponses -> Mono.just(new CommentListResponse(commentContentResponses)));
    }
}
