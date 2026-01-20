package com.calendar.chat.domain.models;

import java.time.LocalDateTime;
import java.util.Map;

public record Message(
        String id,
        String senderId,
        String receiverId,
        String content,
        LocalDateTime timestamp
) {}