package jhhong.gramo.color.domain.auth.handler;

import jhhong.gramo.color.domain.auth.payload.AccessTokenResponse;
import jhhong.gramo.color.domain.auth.payload.AuthRequest;
import jhhong.gramo.color.domain.auth.payload.TokenResponse;
import jhhong.gramo.color.domain.auth.service.AuthService;
import jhhong.gramo.color.global.validator.CustomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@RequiredArgsConstructor
@Component
public class AuthHandler {

    private final AuthService authService;
    private final CustomValidator customValidator;

    public Mono<ServerResponse> createToken(ServerRequest request) {
        return request.bodyToMono(AuthRequest.class)
                .flatMap(customValidator::validate)
                .flatMap(req -> ServerResponse.created(URI.create("/auth")).body(authService.createToken(req), TokenResponse.class));   // 어떤 구조인지 공부
    }

    public Mono<ServerResponse> refreshToken(ServerRequest request) {
        String refreshToken = request.headers().firstHeader("Refresh-Token");
        return ServerResponse.ok().body(authService.refreshToken(refreshToken), AccessTokenResponse.class);
    }
}
