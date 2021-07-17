package jhhong.gramo.color.domain.email.handler;

import jhhong.gramo.color.domain.email.payload.EmailRequest;
import jhhong.gramo.color.domain.email.payload.VerifyRequest;
import jhhong.gramo.color.domain.email.service.EmailService;
import jhhong.gramo.color.global.validator.CustomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class EmailHandler {

    private final EmailService emailService;
    private final CustomValidator<EmailRequest> emailRequestCustomValidator;
    private final CustomValidator<VerifyRequest> verifyRequestCustomValidator;

    public Mono<ServerResponse> sendEmail(ServerRequest request) {
        return ServerResponse.ok().build(request.bodyToMono(EmailRequest.class)
                .doOnNext(emailRequestCustomValidator::validate)
                .doOnNext(emailService::sendEmail)
                .then()`);
    }

    public Mono<ServerResponse> verifyEmail(ServerRequest request) {
        return request.bodyToMono(VerifyRequest.class)
                .doOnNext(verifyRequestCustomValidator::validate)
                .flatMap(emailService::verifyUser)
                .flatMap(res -> ServerResponse.ok().body(res, Void.class));
    }
}
