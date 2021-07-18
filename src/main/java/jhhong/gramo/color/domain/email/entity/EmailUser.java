package jhhong.gramo.color.domain.email.entity;

import jhhong.gramo.color.domain.email.payload.EmailRequest;
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

    @NonNull
    @Indexed(unique = true)
    private String code;

    @NonNull
    @Id
    private final String email;

    @Builder.Default
    private boolean status = false;

    @Builder.Default
    @Indexed(expireAfterSeconds = 180)
    private LocalDateTime expireAt = LocalDateTime.now();

    public EmailUser updateStatus() {
        this.status = true;
        return this;
    }

    public EmailUser update(String code) {
        this.code = code;
        this.expireAt = LocalDateTime.now();
        return this;
    }

}
