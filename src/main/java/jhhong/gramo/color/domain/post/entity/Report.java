package jhhong.gramo.color.domain.post.entity;

import jhhong.gramo.color.domain.post.entity.enums.Feel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Report {

    @Field(name = "_id")
    private ObjectId postId;

    @Id
    @Field(name = "id")
    private ObjectId id;

    @Field(name = "user_email")
    private String userEmail;

    private Feel feel;

    private String reason;

    private String type;

    private LocalDateTime createdAt;
}
