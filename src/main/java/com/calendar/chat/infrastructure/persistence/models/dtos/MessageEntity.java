package com.calendar.chat.infrastructure.persistence.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageEntity {
        @Field("m_id")
        @Builder.Default
        private ObjectId id = new ObjectId();

        @Field("s_id")
        private String senderId;

        @Field("s_un")
        private String senderName;

        @Field("txt")
        private String content;

        @Field("ts")
        private LocalDateTime timestamp;
}