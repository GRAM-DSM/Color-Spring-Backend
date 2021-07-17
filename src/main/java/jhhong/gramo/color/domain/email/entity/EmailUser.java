package jhhong.gramo.color.domain.email.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
@Document
public class EmailUser {

    @Id
    private final String id;

    @NonNull
    @Indexed(unique = true)
    private final String code;

    @NonNull
    @Indexed(unique = true)
    private final String email;

    @Builder.Default
    private boolean status = false;

    @Builder.Default
    @Indexed(expireAfterSeconds = 180)
    private final LocalDateTime expireAt = LocalDateTime.now();

    public EmailUser updateStatus() {
        this.status = true;
        return this;
    }

}
