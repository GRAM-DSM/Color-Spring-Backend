package jhhong.gramo.color.domain.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@Document
public class RefreshToken {

    @Id
    private final String id;

    private final String refreshToken;

    @Builder.Default
    @Indexed(expireAfterSeconds = 1209600)
    private LocalDateTime expireAt = LocalDateTime.now();
}
