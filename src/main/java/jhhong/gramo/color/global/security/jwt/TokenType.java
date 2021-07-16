package jhhong.gramo.color.global.security.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@RequiredArgsConstructor
public enum TokenType {

    ACCESS_TOKEN("access", TokenExpiration.accessExp),
    REFRESH_TOKEN("refresh", TokenExpiration.refreshExp);

    private final String type;
    private final Long exp;
}

@Component
class TokenExpiration {
    public static long accessExp;

    public static long refreshExp;


    @Value("${auth.exp.refresh}")
    private void setRefreshExp(long refreshExp) {
        TokenExpiration.refreshExp = refreshExp;
    }

    @Value("${auth.exp.access}")
    private void setAccessExp(long accessExp) {
        TokenExpiration.accessExp = accessExp;
    }
}