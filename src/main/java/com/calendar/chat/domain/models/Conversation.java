package com.calendar.chat.domain.models;

import java.time.LocalDateTime;
import java.util.List;

public record Conversation(
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