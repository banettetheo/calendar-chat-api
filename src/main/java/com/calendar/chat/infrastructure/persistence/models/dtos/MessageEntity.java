package com.calendar.chat.infrastructure.persistence.models.dtos;

import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;

public record MessageEntity(
        @Field("m_id") String messageId,
        @Field("s_id") String senderId,
        @Field("txt") String content,
        @Field("t") String type,
        @Field("ts") LocalDateTime timestamp,
        Map<String, Integer> reactions,
        AttachmentEntity attachment
) {}