package jhhong.gramo.color.domain.user.entity;

import lombok.Builder;
import lombok.NonNull;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document
public class User {

    @NonNull @Indexed(unique = true)
    private final String email;

    @NonNull
    private final String password;

    @NonNull @Indexed(unique = true)
    private final String nickname;
}
