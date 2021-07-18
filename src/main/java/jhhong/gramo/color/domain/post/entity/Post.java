package jhhong.gramo.color.domain.post.entity;

import jhhong.gramo.color.domain.post.entity.enums.Feel;
import jhhong.gramo.color.domain.post.post.payload.CreatePostRequest;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Post {

    @Id
    private String id;

    @NonNull
    private String nickname;

    @Indexed
    @NonNull
    private String userEmail;

    @NonNull
    private String content;

    @NonNull
    private Feel feel;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private List<String> hashTag;

    private List<Comment> comment;

    private List<String> favorite;

    public Mono<Post> updatePost(CreatePostRequest request) {
        this.content = request.content();
        this.feel = request.feel();
        this.hashTag = request.hashTag();
        return Mono.just(this);
    }

}
