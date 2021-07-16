package jhhong.gramo.color.domain.email.service;

import jhhong.gramo.color.domain.email.payload.EmailRequest;
import reactor.core.publisher.Mono;

public interface EmailService {
    Mono<Void> sendEmail(EmailRequest request);
}
