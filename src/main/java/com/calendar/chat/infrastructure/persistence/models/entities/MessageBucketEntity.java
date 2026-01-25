package com.calendar.chat.infrastructure.persistence.models.entities;

import com.calendar.chat.infrastructure.persistence.models.dtos.MessageEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "message_buckets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageBucketEntity {
    @Id
    private String id;
    private String conversationId;
    private Integer bucketIndex;
    private List<MessageEntity> messages;
}
