package com.calendar.chat.domain.models;

import java.util.List;

public record MessageBucket(
        String conversationId,
        Integer bucketIndex,
        List<Message> messages
) {
}
