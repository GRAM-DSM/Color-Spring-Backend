package jhhong.gramo.color.domain.user.exception;

import jhhong.gramo.color.domain.auth.exception.RefreshTokenNotFoundException;
import jhhong.gramo.color.global.error.ErrorCode;
import jhhong.gramo.color.global.error.GlobalException;

public class UserNotFoundException extends GlobalException {
    public static GlobalException EXCEPTION = new UserNotFoundException();
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
