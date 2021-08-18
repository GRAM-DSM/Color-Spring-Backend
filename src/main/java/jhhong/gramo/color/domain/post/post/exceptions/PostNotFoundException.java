package jhhong.gramo.color.domain.post.post.exceptions;

import jhhong.gramo.color.domain.auth.exception.RefreshTokenNotFoundException;
import jhhong.gramo.color.global.error.ErrorCode;
import jhhong.gramo.color.global.error.GlobalException;

public class PostNotFoundException extends GlobalException {
    public static GlobalException EXCEPTION = new PostNotFoundException();
    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
