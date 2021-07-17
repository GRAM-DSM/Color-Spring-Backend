package jhhong.gramo.color.domain.user.payload;

import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
public record UserRequest(@Email String email,
                          @Size(min = 3, max = 30) String password,
                          @NotBlank String nickname) {
}
