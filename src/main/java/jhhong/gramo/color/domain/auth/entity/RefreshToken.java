package jhhong.gramo.color.domain.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@Document
public class RefreshToken {

    @Id
    private final String id;

    @Indexed(unique = true)
    private final String email;

    private final String refreshToken;

    @Builder.Default
    @Indexed(expireAfterSeconds = 1209600)
    private LocalDateTime expireAt = LocalDateTime.now();

    public Mono<RefreshToken> updateExp() {
        this.expireAt = LocalDateTime.now();
        return Mono.just(this);
    }
}
