package jhhong.gramo.color.global.security.authentication;

import jhhong.gramo.color.domain.user.entity.User;
import jhhong.gramo.color.domain.user.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    private final UserRepository userRepository;

    @Override
    public Mono<User> getUser() {
//        return request.exchange().getPrincipal()
//                .map(principal -> (User) principal);
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .flatMap(auth -> userRepository.findByEmail(auth.getPrincipal().toString()));
    }
}
