package jhhong.gramo.color.domain.email.handler;

import jhhong.gramo.color.domain.email.payload.EmailRequest;
import jhhong.gramo.color.domain.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class EmailHandler {

    private final EmailService emailService;

    public Mono<ServerResponse> sendEmail(ServerRequest request) {

        Mono<Void> result =  request.bodyToMono(EmailRequest.class)
                .flatMap(emailService::sendEmail);

        return ServerResponse.ok().body(result, Void.class);
    }

    public Mono<ServerResponse> verifyEmail(ServerRequest request) {
        return null;
    }
}
