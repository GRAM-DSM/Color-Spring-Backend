package jhhong.gramo.color.domain.post.comment.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class CommentContentResponse {

    private final String id;

    private final String content;

    @JsonProperty("created_at")
    private final LocalDateTime createdAt;

    @JsonProperty("is_mine")
    private final Boolean isMine;

    @JsonProperty("user_nickname")
    private final String userNickname;
}
