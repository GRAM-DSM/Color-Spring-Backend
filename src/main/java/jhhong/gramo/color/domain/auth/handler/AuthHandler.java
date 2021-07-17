package jhhong.gramo.color.domain.auth.handler;

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
                .flatMap(authService::createToken)
                .flatMap(tokenResponse -> ServerResponse.created(URI.create("/auth")).body(tokenResponse, TokenResponse.class));
    }
}
