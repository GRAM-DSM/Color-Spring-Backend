package jhhong.gramo.color.domain.email.exceptions;

import jhhong.gramo.color.global.error.ErrorCode;
import jhhong.gramo.color.global.error.GlobalException;

public class NumberNotFoundException extends GlobalException {
    public NumberNotFoundException() {
        super(ErrorCode.CODE_NOT_FOUND);
    }
}
