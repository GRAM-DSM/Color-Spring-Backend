package jhhong.gramo.color.domain.email.payload;

import lombok.NonNull;

public record VerifyRequest(
        @NonNull String email,
        @NonNull String code)
{ }
