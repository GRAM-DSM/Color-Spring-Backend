package jhhong.gramo.color.domain.email.router;

import jhhong.gramo.color.domain.email.handler.EmailHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@Configuration
public class EmailRouter {

    private final EmailHandler emailHandler;

    @Bean
    public RouterFunction<ServerResponse> emailRoute() {
        return route().path("/email",
                builder -> builder.nest(accept(MediaType.APPLICATION_JSON), routes -> routes
                    .POST("", emailHandler::sendEmail)
                    .GET("", emailHandler::verifyEmail)))
                .build();
    }
}
