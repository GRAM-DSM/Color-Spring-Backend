package jhhong.gramo.color.domain.user.payload;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

public record CheckNicknameRequest(@NotBlank String nickname) {
}
