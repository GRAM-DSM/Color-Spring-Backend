package jhhong.gramo.color.domain.auth.service;

import jhhong.gramo.color.domain.auth.payload.AuthRequest;
import jhhong.gramo.color.domain.auth.payload.TokenResponse;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<TokenResponse> createToken(AuthRequest authRequest);
}
