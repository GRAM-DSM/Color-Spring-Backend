package jhhong.gramo.color.domain.post.post.service;

import jhhong.gramo.color.domain.post.entity.Post;
import jhhong.gramo.color.domain.post.entity.PostRepository;
import jhhong.gramo.color.domain.post.entity.enums.Feel;
import jhhong.gramo.color.domain.post.post.exceptions.InvalidAccessException;
import jhhong.gramo.color.domain.post.post.exceptions.PostNotFoundException;
import jhhong.gramo.color.domain.post.post.payload.CreatePostRequest;
import jhhong.gramo.color.domain.post.post.payload.PostContentResponse;
import jhhong.gramo.color.domain.post.post.payload.PostListResponse;
import jhhong.gramo.color.domain.user.entity.UserRepository;
import jhhong.gramo.color.domain.user.exception.UserNotFoundException;
import jhhong.gramo.color.global.security.authentication.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public Mono<Void> createPost(CreatePostRequest request) {
        return authenticationFacade.getUser()
                .flatMap(user -> userRepository.findById(user.getEmail()))
                .flatMap(user -> buildPost(request))
                .flatMap(postRepository::save)
                .switchIfEmpty(Mono.error(UserNotFoundException::new))
                .then();
    }

    @Override
    public Mono<Void> updatePost(String id, CreatePostRequest request) {
        Mono<Post> postMono = postRepository.findById(id)
                .switchIfEmpty(Mono.error(PostNotFoundException::new));

        Mono<Post> filteredPostMono = postMono
                .zipWith(authenticationFacade.getUser())
                .filter(infos -> infos.getT1().getUserEmail().equals(infos.getT2().getEmail()))
                .map(Tuple2::getT1)
                .switchIfEmpty(Mono.error(InvalidAccessException::new));

        return filteredPostMono
                .flatMap(post -> post.updatePost(request))
                .flatMap(postRepository::save).then();

    }

    @Override
    public Mono<Void> deletePost(String id) {
        Mono<Post> postMono = postRepository.findById(id)
                .switchIfEmpty(Mono.error(PostNotFoundException::new));

        return authenticationFacade.getUser()
                .zipWith(postMono)
                .filter(zip -> zip.getT2().getUserEmail().equals(zip.getT1().getEmail()))
                .switchIfEmpty(Mono.error(InvalidAccessException::new))
                .flatMap(zip -> postRepository.delete(zip.getT2()));
    }

    @Override
    public Mono<PostListResponse> getPost(Feel feel, int page) {
        Flux<PostContentResponse> postContentResponseFlux = postRepository.findAllByFeelOrderByCreatedAtDesc(feel, PageRequest.of(page, 6))
                .flatMap(this::buildPostResponse);

        return postContentResponseFlux.collectList()
                .flatMap(this::buildPostListResponse);
    }

    private Mono<PostListResponse> buildPostListResponse(List<PostContentResponse> postContentResponseList) {
        return Mono.just(PostListResponse.builder()
                .postContentResponseList(postContentResponseList)
                .totalPages(postContentResponseList.size())
                .build());
    }

    private Mono<PostContentResponse> buildPostResponse(Post post) {
        return authenticationFacade.getUser()
                .map(user ->
                        PostContentResponse.builder()
                                .commentCnt(post.getComment() == null ? 0 : post.getComment().size())
                                .content(post.getContent())
                                .createdAt(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")))
                                .favoriteCnt(post.getFavorite() == null ? 0 : post.getFavorite().size())
                                .hashCode(post.getHashTag())
                                .id(post.getId())
                                .isFavorite(post.getFavorite() != null && post.getFavorite().stream().anyMatch(s -> s.equals(user.getEmail())))
                                .isMine(post.getUserEmail().equals(user.getEmail()))
                                .userNickname(user.getNickname())
                                .build());
    }

    private Mono<Post> buildPost(CreatePostRequest request) {
        return authenticationFacade.getUser()
                .map(user -> Post.builder()
                        .content(request.content())
                        .feel(request.feel())
                        .nickname(user.getNickname())
                        .hashTag(request.hashTag())
                        .comment(new ArrayList<>())
                        .favorite(new ArrayList<>())
                        .userEmail(user.getEmail())
                        .build());
    }
}
