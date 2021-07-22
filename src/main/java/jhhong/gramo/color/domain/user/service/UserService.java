package jhhong.gramo.color.domain.user.service;

import jhhong.gramo.color.domain.user.payload.CheckNicknameRequest;
import jhhong.gramo.color.domain.user.payload.UserRequest;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<Void> createUser(UserRequest request);
    Mono<Void> checkNickname(String nickname);
}
