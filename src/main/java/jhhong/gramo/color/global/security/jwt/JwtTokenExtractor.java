package jhhong.gramo.color.global.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public record JwtTokenExtractor(
        JwtVerifier jwtVerifier) implements ServerAuthenticationConverter {

    private Mono<String> extractToken(String token) {
        return Mono.justOrEmpty(token.substring(7));
    }

    private Mono<String> getToken(ServerWebExchange exchange) {
        return Mono.justOrEmpty(
                exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange)
                .flatMap(this::getToken)
                .flatMap(this::extractToken)
                .flatMap(jwtVerifier::check);
    }
}
