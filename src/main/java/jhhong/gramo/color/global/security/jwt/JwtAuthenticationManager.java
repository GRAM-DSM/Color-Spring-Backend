package jhhong.gramo.color.global.security.jwt;

import jhhong.gramo.color.domain.user.entity.UserRepository;
import jhhong.gramo.color.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final UserRepository userRepository;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .flatMap(auth -> Mono.just(auth.getName()))
                .flatMap(userRepository::findByEmail)
                .flatMap(user -> Mono.just(authentication))
                .switchIfEmpty(Mono.error(new UserNotFoundException()));
    }
}
