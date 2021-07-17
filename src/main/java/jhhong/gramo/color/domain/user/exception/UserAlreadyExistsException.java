package jhhong.gramo.color.domain.user.exception;

import jhhong.gramo.color.global.error.ErrorCode;
import jhhong.gramo.color.global.error.GlobalException;

public class UserAlreadyExistsException extends GlobalException {
    public UserAlreadyExistsException() {
        super(ErrorCode.USER_ALREADY_EXIST);
    }
}
