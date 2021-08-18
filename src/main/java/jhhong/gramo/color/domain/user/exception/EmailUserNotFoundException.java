package jhhong.gramo.color.domain.user.exception;

import jhhong.gramo.color.domain.auth.exception.RefreshTokenNotFoundException;
import jhhong.gramo.color.global.error.ErrorCode;
import jhhong.gramo.color.global.error.GlobalException;

public class EmailUserNotFoundException extends GlobalException {
    public static GlobalException EXCEPTION = new EmailUserNotFoundException();
    public EmailUserNotFoundException() {
        super(ErrorCode.EMAIL_USER_NOT_FOUND);
    }
}
