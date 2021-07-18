package jhhong.gramo.color.domain.post.post.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jhhong.gramo.color.domain.post.entity.enums.Feel;

import javax.validation.constraints.NotBlank;
import java.util.List;

public record CreatePostRequest(@NotBlank String content,
                                Feel feel,
                                @JsonProperty("hash_tag") List<String> hashTag) {
}
