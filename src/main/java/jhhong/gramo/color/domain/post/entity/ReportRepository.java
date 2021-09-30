package jhhong.gramo.color.domain.post.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import reactor.core.publisher.Mono;

public interface ReportRepository extends MongoRepository<Report, ObjectId> {
    Mono<Void> deleteAllByPostId(ObjectId postId);
}
