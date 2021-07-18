package jhhong.gramo.color.domain.post.entity;

import jhhong.gramo.color.domain.post.entity.enums.Feel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PostRepository extends ReactiveMongoRepository<Post, String> {
    Flux<Post> findAllByFeel(Feel feel, Pageable pageable);
}
