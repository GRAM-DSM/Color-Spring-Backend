package jhhong.gramo.color.domain.post.post.exceptions;

import jhhong.gramo.color.global.error.ErrorCode;
import jhhong.gramo.color.global.error.GlobalException;

public class PostNotFoundException extends GlobalException {
    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
