package com.calendar.chat.application.dtos;

import java.time.LocalDateTime;

public record MessageResponse(
        String id,
        String senderId,
        String senderName,
        String content,
        LocalDateTime timestamp) {
}
