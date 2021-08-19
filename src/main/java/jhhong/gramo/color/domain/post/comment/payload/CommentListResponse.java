package jhhong.gramo.color.domain.post.comment.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentListResponse {
    private List<CommentContentResponse> commentContentResponseList;
}
