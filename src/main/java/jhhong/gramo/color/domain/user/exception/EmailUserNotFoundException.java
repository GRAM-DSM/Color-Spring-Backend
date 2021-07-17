package jhhong.gramo.color.domain.user.exception;

import jhhong.gramo.color.global.error.ErrorCode;
import jhhong.gramo.color.global.error.GlobalException;

public class EmailUserNotFoundException extends GlobalException {
    public EmailUserNotFoundException() {
        super(ErrorCode.EMAIL_USER_NOT_FOUND);
    }
}
