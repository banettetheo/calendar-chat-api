package com.calendar.chat.infrastructure.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "message_buckets")
@AllArgsConstructor
@Getter
@Setter
public class MessageBucketEntity {
    @Id
    private String id; // Format: "convId_bucketId"
    private String conversationId;
    private int bucketId;
    //private List<Message> messages;
    private boolean isFull;
}
