package jhhong.gramo.color.domain.auth.entity;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends ReactiveMongoRepository<RefreshToken, String> {
}
