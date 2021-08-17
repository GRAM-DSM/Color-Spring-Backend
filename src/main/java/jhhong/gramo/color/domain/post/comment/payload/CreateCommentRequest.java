package jhhong.gramo.color.domain.post.comment.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class CreateCommentRequest {
    @NotBlank
    private final String content;
}
