package jhhong.gramo.color.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${auth.secret}")
    private String secret;

    public Mono<String> generateToken(String email, TokenType tokenType) {
        return Mono.just(
                Jwts.builder()
                        .setSubject(email)
                        .signWith(SignatureAlgorithm.HS256, getSecret())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + tokenType.getExp() * 1000))
                        .claim("type", tokenType.getType())
                        .compact()
        );
    }

    public Mono<Claims> parseToken(String token) {
        try {
            return Mono.just(Jwts.parser().setSigningKey(getSecret())
                    .parseClaimsJws(token).getBody());
        } catch (Exception e) {
            return Mono.error(InvalidTokenException.EXCEPTION);
        }
    }

    private byte[] getSecret() {
        return Base64.getEncoder().encode(secret.getBytes());
    }
}
