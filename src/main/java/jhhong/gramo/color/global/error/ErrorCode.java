package jhhong.gramo.color.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    BAD_REQUEST(400, "Bad Request. Check json"),
    USER_NOT_FOUND(404, "User Not Found Exception"),
    EMAIL_USER_NOT_FOUND(404, "Email User Not Found"),
    REFRESH_TOKEN_NOT_FOUND(404, "Refresh Token Not Found"),
    USER_ALREADY_EXIST(409, "User Already Exist"),
    VERIFY_USER_NOT_FOUND(404, "User Not Verified"),
    NICKNAME_ALREADY_EXIST(409, "Nickname Already Exist"),
    CODE_NOT_FOUND(404, "Verify Code Not Found"),
    USER_NOT_WRITER(403, "User is not the writer"),
    POST_NOT_FOUND(404, "Post Not Found"),
    INVALID_TOKEN(401, "Invalid Token"),
    INVALID_ACCESS(401, "Invalid Access"),
    EMAIL_SEND_FAILED(500, "Email Send Failed"),
    COMMENT_NOT_FOUND(404, "Comment Not Found");

    private final int code;
    private final String message;
}
