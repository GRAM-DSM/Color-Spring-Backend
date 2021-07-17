package jhhong.gramo.color.domain.user.entity;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<Boolean> existsByEmailOrNickname(String email, String nickname);
    Mono<User> findByEmail(String email);
    Mono<Boolean> existsByNickname(String nickname);
}
