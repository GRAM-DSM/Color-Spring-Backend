package jhhong.gramo.color.domain.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document
public class User {

    @Id
    @NonNull @Indexed(unique = true)
    private final String email;

    @NonNull
    private final String password;

    @NonNull @Indexed(unique = true)
    private final String nickname;
}
