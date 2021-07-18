package jhhong.gramo.color.domain.post.comment.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CommentListResponse {
    private final List<CommentContentResponse> commentContentResponseList;
}
