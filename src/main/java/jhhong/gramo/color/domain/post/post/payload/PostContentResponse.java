package jhhong.gramo.color.domain.post.post.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostContentResponse {
    private String id;

    private String content;

    @JsonProperty("user_nickname")
    private String userNickname;

    @JsonProperty("is_mine")
    private Boolean isMine;

    @JsonProperty("comment_cnt")
    private int commentCnt;

    @JsonProperty("favorite_cnt")
    private int favoriteCnt;

    @JsonProperty("is_favorite")
    private Boolean isFavorite;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("hash_code")
    private List<String> hashCode;
}
