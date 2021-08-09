package jhhong.gramo.color.domain.post.comment.router;

import jhhong.gramo.color.domain.post.comment.handler.CommentHandler;
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
public class CommentRouter {

    private final CommentHandler commentHandler;

    @Bean
    public RouterFunction<ServerResponse> commentRoute() {
        return route().path("/comment",
                builder -> builder.nest(accept(MediaType.APPLICATION_JSON), routes -> routes
                        .DELETE("/{post_id}/{comment_id}", commentHandler::deleteComment)
                        .GET("", commentHandler::getComment)
                        .POST("/${post_id}", commentHandler::createComment)))
                .build();
    }
}
