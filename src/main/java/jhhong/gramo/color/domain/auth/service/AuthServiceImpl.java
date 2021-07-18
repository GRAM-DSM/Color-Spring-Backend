package jhhong.gramo.color.domain.auth.service;

import io.jsonwebtoken.Claims;
import jhhong.gramo.color.domain.auth.entity.RefreshToken;
import jhhong.gramo.color.domain.auth.entity.RefreshTokenRepository;
import jhhong.gramo.color.domain.auth.exception.RefreshTokenNotFoundException;
import jhhong.gramo.color.domain.auth.payload.AccessTokenResponse;
import jhhong.gramo.color.domain.auth.payload.AuthRequest;
import jhhong.gramo.color.domain.auth.payload.TokenResponse;
import jhhong.gramo.color.domain.user.entity.UserRepository;
import jhhong.gramo.color.domain.user.exception.UserNotFoundException;
import jhhong.gramo.color.global.security.jwt.JwtTokenProvider;
import jhhong.gramo.color.global.security.jwt.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<TokenResponse> createToken(AuthRequest authRequest) {
        return userRepository.findByEmail(authRequest.email())
                .filter(user -> passwordEncoder.matches(authRequest.password(), user.getPassword()))
                .flatMap(user -> saveIfEmpty(user.getEmail()))
                .zipWith(jwtTokenProvider.generateToken(authRequest.email(), TokenType.ACCESS_TOKEN))
                .flatMap(tokens -> Mono.just(new TokenResponse(tokens.getT1().getRefreshToken(), tokens.getT2())))
                .switchIfEmpty(Mono.error(UserNotFoundException::new));
    }

    @Override
    public Mono<AccessTokenResponse> refreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .flatMap(RefreshToken::updateExp)
                .flatMap(refreshTokenRepository::save)
                .flatMap(token -> jwtTokenProvider.parseToken(token.getRefreshToken()))
                .map(Claims::getSubject)
                .flatMap(email -> jwtTokenProvider.generateToken(email, TokenType.ACCESS_TOKEN))
                .flatMap(accessToken -> Mono.just(new AccessTokenResponse(accessToken)))
                .switchIfEmpty(Mono.error(RefreshTokenNotFoundException::new));
    }

    private Mono<RefreshToken> buildRefreshToken(String email) {
        return jwtTokenProvider.generateToken(email, TokenType.REFRESH_TOKEN)
                .map(token -> RefreshToken.builder()
                        .refreshToken(token)
                        .email(email)
                        .build());
    }

    private Mono<RefreshToken> saveIfEmpty(String email) {
        Mono<String> refreshToken = jwtTokenProvider.generateToken(email, TokenType.REFRESH_TOKEN);

        Mono<RefreshToken> refreshTokenMono = refreshTokenRepository.findByEmail(email)
                .zipWith(refreshToken)
                .flatMap(refresh -> refresh.getT1().updateRefreshToken(refresh.getT2()))
                .switchIfEmpty(this.buildRefreshToken(email));

        return refreshTokenMono.flatMap(refreshTokenRepository::save);
    }

}
