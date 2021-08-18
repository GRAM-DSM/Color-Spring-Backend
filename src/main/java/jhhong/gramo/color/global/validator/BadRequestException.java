package jhhong.gramo.color.global.validator;

import jhhong.gramo.color.domain.auth.exception.RefreshTokenNotFoundException;
import jhhong.gramo.color.global.error.ErrorCode;
import jhhong.gramo.color.global.error.GlobalException;

public class BadRequestException extends GlobalException {
    public static GlobalException EXCEPTION = new BadRequestException();
    public BadRequestException() {
        super(ErrorCode.BAD_REQUEST);
    }
}
