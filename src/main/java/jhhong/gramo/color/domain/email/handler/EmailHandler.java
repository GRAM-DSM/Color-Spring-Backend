package jhhong.gramo.color.domain.email.handler;

import jhhong.gramo.color.domain.email.payload.EmailRequest;
import jhhong.gramo.color.domain.email.service.EmailService;
import jhhong.gramo.color.global.validator.CustomValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@Component
public class EmailHandler {

    private final EmailService emailService;
    private final CustomValidator emailRequestCustomValidator;

    public Mono<ServerResponse> sendEmail(ServerRequest request) {
        return ServerResponse.ok().body(
                request.bodyToMono(EmailRequest.class)
                        .flatMap(emailRequestCustomValidator::validate)
                        .doOnNext(req -> Mono.defer(() -> emailService.sendEmail(req)
                                .subscribeOn(Schedulers.boundedElastic())).subscribe())
                        .then(), Void.class);
    }

    public Mono<ServerResponse> verifyEmail(ServerRequest request) {
        @NonNull
        String email = request.pathVariable("email");

        @NonNull
        String code = request.pathVariable("code");

        return emailService.verifyUser(email, code)
                .flatMap(res -> ServerResponse.ok().body(res, Void.class));
    }
}
