package jhhong.gramo.color.domain.auth.service;

import jhhong.gramo.color.domain.auth.entity.RefreshTokenRepository;
import jhhong.gramo.color.domain.auth.payload.AuthRequest;
import jhhong.gramo.color.domain.auth.payload.TokenResponse;
import jhhong.gramo.color.domain.user.entity.User;
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
                .flatMap(user -> jwtTokenProvider.generateToken(user.getEmail(), TokenType.REFRESH_TOKEN))
                .zipWith(jwtTokenProvider.generateToken(authRequest.email(), TokenType.ACCESS_TOKEN))
                .flatMap(tokens -> Mono.just(new TokenResponse(tokens.getT1(), tokens.getT2())))
                .switchIfEmpty(Mono.error(UserNotFoundException::new));
    }
}
