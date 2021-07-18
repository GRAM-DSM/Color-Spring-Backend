package jhhong.gramo.color.domain.post.comment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class Comment {

    @NonNull
    private final String id;

    @NonNull
    private final String userEmail;

    @Builder.Default
    @NonNull
    private final LocalDateTime createdAt = LocalDateTime.now();

    @NonNull
    private final String content;

    @NonNull
    private final String userNickname;

}
