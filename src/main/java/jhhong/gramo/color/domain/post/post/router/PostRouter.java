package jhhong.gramo.color.domain.post.post.router;

import jhhong.gramo.color.domain.post.post.handler.PostHandler;
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
public class PostRouter {

    private final PostHandler postHandler;

    @Bean
    public RouterFunction<ServerResponse> postRoute() {
        return route().path("/post",
                builder -> builder.nest(accept(MediaType.APPLICATION_JSON), routes -> routes
                        .POST("", postHandler::createPost)
                        .GET("", postHandler::getPost)
                        .PUT("/{postId}", postHandler::updatePost)
                        .DELETE("/{postId}", postHandler::deletePost)))
                .build();
    }

}
