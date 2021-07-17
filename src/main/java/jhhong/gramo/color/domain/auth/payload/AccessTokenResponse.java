package jhhong.gramo.color.domain.auth.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccessTokenResponse(@JsonProperty("access_token") String accessToken){
}
