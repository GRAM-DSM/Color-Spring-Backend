package jhhong.gramo.color.domain.post.comment.exception;

import jhhong.gramo.color.domain.auth.exception.RefreshTokenNotFoundException;
import jhhong.gramo.color.global.error.ErrorCode;
import jhhong.gramo.color.global.error.GlobalException;

public class CommentNotFoundException extends GlobalException {
    public static GlobalException EXCEPTION = new CommentNotFoundException();
    public CommentNotFoundException() {
        super(ErrorCode.COMMENT_NOT_FOUND);
    }
}
