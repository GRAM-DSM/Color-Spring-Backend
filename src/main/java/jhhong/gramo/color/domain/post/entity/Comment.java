package jhhong.gramo.color.domain.post.entity;

import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;

@Builder
public record Comment(@NonNull String userEmail, @NonNull LocalDateTime createdAt, @NonNull String content,
                      @NonNull String userNickname) {

}
