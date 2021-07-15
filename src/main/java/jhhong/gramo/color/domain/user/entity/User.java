package jhhong.gramo.color.domain.user.entity;

import lombok.Builder;
import lombok.NonNull;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document
public record User(
        @NonNull @Indexed(unique = true) String email,
        @NonNull String password,
        @NonNull @Indexed(unique = true) String nickname) {
}
