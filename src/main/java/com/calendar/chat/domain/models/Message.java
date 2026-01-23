package com.calendar.chat.domain.models;

import java.time.LocalDateTime;

public record Message(
        String id,
        String senderId,
        String senderName,
        String receiverId,
        String conversationId,
        String content,
        LocalDateTime timestamp
) {}