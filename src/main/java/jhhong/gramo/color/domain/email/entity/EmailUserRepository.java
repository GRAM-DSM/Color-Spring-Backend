package jhhong.gramo.color.domain.email.entity;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface EmailUserRepository extends ReactiveMongoRepository<EmailUser, String> {
    Mono<EmailUser> findByEmailAndCode(String email, String code);
    Mono<Boolean> existsByEmail(String email);
}
