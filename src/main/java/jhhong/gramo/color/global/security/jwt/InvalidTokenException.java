package jhhong.gramo.color.global.security.jwt;

import jhhong.gramo.color.domain.auth.exception.RefreshTokenNotFoundException;
import jhhong.gramo.color.global.error.ErrorCode;
import jhhong.gramo.color.global.error.GlobalException;

public class InvalidTokenException extends GlobalException {
    public static GlobalException EXCEPTION = new InvalidTokenException();
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
