package com.calendar.chat.domain.models;

import java.time.LocalDateTime;
import java.util.List;

public record ConversationSummary(
    String id,
    List<String> participantIds,
    ConversationType type,
    LocalDateTime updatedAt,
    Message lastMessage,
    boolean isFull
) {
    public boolean hasParticipant(String userId) {
        return participantIds.contains(userId);
    }
}