package jhhong.gramo.color.domain.auth.entity;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RefreshTokenRepository extends ReactiveMongoRepository<RefreshToken, String> {
    Mono<RefreshToken> findByRefreshToken(String refreshToken);

    Mono<RefreshToken> findByEmail(String email);
}
