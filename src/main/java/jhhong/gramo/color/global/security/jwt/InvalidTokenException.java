package jhhong.gramo.color.global.security.jwt;

import jhhong.gramo.color.global.error.ErrorCode;
import jhhong.gramo.color.global.error.GlobalException;

public class InvalidTokenException extends GlobalException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
