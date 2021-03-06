package jhhong.gramo.color.domain.email.exceptions;

import jhhong.gramo.color.domain.auth.exception.RefreshTokenNotFoundException;
import jhhong.gramo.color.global.error.ErrorCode;
import jhhong.gramo.color.global.error.GlobalException;

public class EmailSendFailException extends GlobalException {
    public static GlobalException EXCEPTION = new EmailSendFailException();
    public EmailSendFailException() {
        super(ErrorCode.EMAIL_SEND_FAILED);
    }
}
