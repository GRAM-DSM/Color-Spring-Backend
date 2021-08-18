package jhhong.gramo.color.domain.email.exceptions;

import jhhong.gramo.color.domain.auth.exception.RefreshTokenNotFoundException;
import jhhong.gramo.color.global.error.ErrorCode;
import jhhong.gramo.color.global.error.GlobalException;

public class NumberNotFoundException extends GlobalException {
    public static GlobalException EXCEPTION = new NumberNotFoundException();
    public NumberNotFoundException() {
        super(ErrorCode.CODE_NOT_FOUND);
    }
}
