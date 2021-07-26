package jhhong.gramo.color.domain.auth.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record AuthRequest(@Email String email,
                          @NotBlank String password,
                          @JsonProperty("device_token") @NotBlank String deviceToken) {
}
