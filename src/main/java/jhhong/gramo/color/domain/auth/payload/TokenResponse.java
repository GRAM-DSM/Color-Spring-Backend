package jhhong.gramo.color.domain.auth.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(@JsonProperty("access_token") String accessToken,
                            @JsonProperty("refresh_token") String refreshToken) {
}
