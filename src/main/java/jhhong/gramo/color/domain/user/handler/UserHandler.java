package jhhong.gramo.color.domain.user.handler;

import jhhong.gramo.color.domain.user.payload.CheckNicknameRequest;
import jhhong.gramo.color.domain.user.payload.UserRequest;
import jhhong.gramo.color.domain.user.service.UserService;
import jhhong.gramo.color.global.validator.CustomValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@RequiredArgsConstructor
@Component
public class UserHandler {

    private final UserService userService;
    private final CustomValidator customValidator;

    public Mono<ServerResponse> signUp(ServerRequest request) {
        return request.bodyToMono(UserRequest.class)
                .flatMap(customValidator::validate)
                .flatMap(userRequest -> ServerResponse.created(URI.create("/user")).body(userService.createUser(userRequest), Void.class));
    }

    public Mono<ServerResponse> checkNickname(ServerRequest request) {
        return Mono.just(request.pathVariable("nickname"))
                .flatMap((@NonNull var userRequest) -> ServerResponse.ok().build(userService.checkNickname(userRequest)));
    }

}
