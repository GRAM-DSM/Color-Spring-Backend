package jhhong.gramo.color.domain.email.payload;

import javax.validation.constraints.Email;

public record EmailRequest(@Email(message = "Email Format Error") String email){}
