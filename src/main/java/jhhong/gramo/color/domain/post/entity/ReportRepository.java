package jhhong.gramo.color.domain.post.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ReportRepository extends ReactiveMongoRepository<Report, ObjectId> {
    Mono<Void> deleteAllByPostId(ObjectId postId);
}
