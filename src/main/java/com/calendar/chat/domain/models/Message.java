package com.calendar.chat.domain.models;

import java.time.LocalDateTime;
import java.util.Map;

public record Message(
        String id,
        String senderId,
        String content,
        MessageType type, // TEXT, IMAGE, VIDEO, etc.
        LocalDateTime timestamp,
        Map<String, Integer> reactions
) {}