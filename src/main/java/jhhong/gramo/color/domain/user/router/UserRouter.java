package jhhong.gramo.color.domain.user.router;

import jhhong.gramo.color.domain.user.handler.UserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@Configuration
public class UserRouter {

    private final UserHandler userHandler;

    @Bean
    public RouterFunction<ServerResponse> userRouters() {
        return route().path("/user",
                builder -> builder.nest(accept(MediaType.APPLICATION_JSON), router -> router
                .POST("", userHandler::signUp)))
                .build();
    }
}
