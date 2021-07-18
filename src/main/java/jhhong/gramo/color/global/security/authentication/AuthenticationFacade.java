package jhhong.gramo.color.global.security.authentication;

import jhhong.gramo.color.domain.user.entity.User;
import reactor.core.publisher.Mono;

public interface AuthenticationFacade {
    Mono<User> getUser();
}
