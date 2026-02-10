package com.calendar.chat.application.dtos;

import com.calendar.chat.domain.models.ConversationType;
import java.time.LocalDateTime;

public record ConversationSummaryResponse(
        String id,
        ConversationType type,
        String title,
        LocalDateTime updatedAt,
        MessageResponse lastMessage) {
}
