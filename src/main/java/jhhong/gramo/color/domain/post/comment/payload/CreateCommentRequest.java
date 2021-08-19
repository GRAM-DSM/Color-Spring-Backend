package jhhong.gramo.color.domain.post.comment.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest {
    @NotBlank
    private String content;
}
