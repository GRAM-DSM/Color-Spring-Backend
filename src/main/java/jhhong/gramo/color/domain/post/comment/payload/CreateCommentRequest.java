package jhhong.gramo.color.domain.post.comment.payload;

import javax.validation.constraints.NotBlank;

public record CreateCommentRequest(@NotBlank String content) {
}
