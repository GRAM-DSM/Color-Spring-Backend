package jhhong.gramo.color.domain.post.post.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostListResponse {

    private int totalPages;

    private List<PostContentResponse> postContentResponseList;

}
