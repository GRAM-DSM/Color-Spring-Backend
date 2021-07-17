package jhhong.gramo.color.domain.email.payload;

import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record VerifyRequest(
        @Email(message = "Email Format Mismatch") String email,
        @NotBlank(message = "Code Must Not be blank") String code)
{ }
