package com.calendar.chat.domain.models;

import java.time.LocalDateTime;
import java.util.Map;

public record Message(
        String id,
        String senderId,
        String senderUsername,
        String receiverId,
        String conversationId,
        String content,
        LocalDateTime timestamp
) {}