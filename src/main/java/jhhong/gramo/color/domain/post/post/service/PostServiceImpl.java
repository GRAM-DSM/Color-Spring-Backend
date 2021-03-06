package jhhong.gramo.color.domain.post.post.service;

import jhhong.gramo.color.domain.post.entity.Post;
import jhhong.gramo.color.domain.post.entity.PostRepository;
import jhhong.gramo.color.domain.post.entity.ReportRepository;
import jhhong.gramo.color.domain.post.entity.enums.Feel;
import jhhong.gramo.color.domain.post.post.exceptions.InvalidAccessException;
import jhhong.gramo.color.domain.post.post.exceptions.PostNotFoundException;
import jhhong.gramo.color.domain.post.post.payload.CreatePostRequest;
import jhhong.gramo.color.domain.post.post.payload.PostContentResponse;
import jhhong.gramo.color.domain.post.post.payload.PostListResponse;
import jhhong.gramo.color.domain.user.entity.UserRepository;
import jhhong.gramo.color.domain.user.exception.UserNotFoundException;
import jhhong.gramo.color.global.security.authentication.AuthenticationFacade;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// 2021-08-23 DSM 임베디드과 안은결
@Getter
class Sexy {
    private boolean isSexy = false;
    public void doYouSexy(String answer){
        if(answer.equalsIgnoreCase("y")) {
            this.isSexy = true;
        }
        else isSexy = false;
    }
}

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final ReportRepository reportRepository;
    private final Sexy sexy = new Sexy();

    @Override
    public Mono<Void> createPost(CreatePostRequest request) {
        sexy.doYouSexy("y");
        System.out.println(sexy.isSexy());
        return authenticationFacade.getUser()
                .flatMap(user -> userRepository.findById(user.getEmail()))
                .flatMap(user -> buildPost(request))
                .flatMap(postRepository::save)
                .switchIfEmpty(Mono.error(UserNotFoundException.EXCEPTION))
                .then();
    }

    @Override
    public Mono<Void> updatePost(String id, CreatePostRequest request) {
        Mono<Post> postMono = postRepository.findById(id)
                .switchIfEmpty(Mono.error(PostNotFoundException.EXCEPTION));

        Mono<Post> filteredPostMono = postMono
                .zipWith(authenticationFacade.getUser())
                .filter(infos -> infos.getT1().getUserEmail().equals(infos.getT2().getEmail()))
                .map(Tuple2::getT1)
                .switchIfEmpty(Mono.error(InvalidAccessException.EXCEPTION));

        return filteredPostMono
                .flatMap(post -> post.updatePost(request))
                .flatMap(postRepository::save).then();

    }

    @Override
    public Mono<Void> deletePost(String id) {
        Mono<Post> postMono = postRepository.findById(id)
                .switchIfEmpty(Mono.error(PostNotFoundException.EXCEPTION));

        return authenticationFacade.getUser()
                .zipWith(postMono)
                .filter(zip -> zip.getT2().getUserEmail().equals(zip.getT1().getEmail()))
                .switchIfEmpty(Mono.error(InvalidAccessException.EXCEPTION))
                .flatMap(zip -> postRepository.delete(zip.getT2()))
                .zipWith(postMono)
                .flatMap(objects -> reportRepository.deleteAllByPostId(new ObjectId(objects.getT2().getId())));
    }

    @Override
    public Mono<PostListResponse> getPost(Feel feel, int page) {
        return postRepository.findAllByFeelOrderByCreatedAtDesc(feel, PageRequest.of(page, 6))
                .flatMapSequential(this::buildPostResponse)
                .collectList()
                .map(this::buildPostListResponse);
    }

    private PostListResponse buildPostListResponse(List<PostContentResponse> postContentResponseList) {
        return PostListResponse.builder()
                .postContentResponseList(postContentResponseList)
                .totalPages(postContentResponseList.size() / 6 + 1)
                .build();
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
