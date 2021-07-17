package jhhong.gramo.color.domain.email.service;

import jhhong.gramo.color.domain.email.payload.EmailRequest;
import jhhong.gramo.color.domain.email.payload.VerifyRequest;
import reactor.core.publisher.Mono;

public interface EmailService {
    void sendEmail(EmailRequest request);

    Mono<Void> verifyUser(VerifyRequest request);
}
