package jhhong.gramo.color.domain.auth.exception;

import jhhong.gramo.color.global.error.ErrorCode;
import jhhong.gramo.color.global.error.GlobalException;

public class RefreshTokenNotFoundException extends GlobalException {
    public static GlobalException EXCEPTION = new RefreshTokenNotFoundException();
    public RefreshTokenNotFoundException() {
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
