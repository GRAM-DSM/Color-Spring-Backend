package jhhong.gramo.color.domain.email.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Repository
public class EmailUserExportRepository {

    private final EmailUserRepository emailUserRepository;

    public Mono<EmailUser> findByEmail(String email) {
        return emailUserRepository.findByEmail(email);
    }
}
