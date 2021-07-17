package jhhong.gramo.color.domain.auth.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record AuthRequest(@Email String email,
                          @NotBlank String password) {
}
