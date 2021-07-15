package jhhong.gramo.color.global.security.jwt;

import io.jsonwebtoken.Claims;
import jhhong.gramo.color.global.security.authentication.AuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class JwtVerifier {

    private final JwtTokenProvider jwtTokenProvider;

    public Mono<AuthenticationToken> check(String token) {
        return Mono.just(jwtTokenProvider.parseToken(token))
                .flatMap(this::validateToken)
                .flatMap(claims -> Mono.just(new AuthenticationToken(claims.getSubject(), token)));
    }

    private Mono<Claims> validateToken(Mono<Claims> claimsMono) {
        return claimsMono
                .filter(claims -> claims.get("type").equals(TokenType.ACCESS_TOKEN.getType()))
                .switchIfEmpty(Mono.error(InvalidTokenException::new));
    }
}
