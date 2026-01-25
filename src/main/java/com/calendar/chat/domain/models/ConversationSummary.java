package com.calendar.chat.domain.models;

import java.time.LocalDateTime;
import java.util.List;

public record ConversationSummary(
    String id,
    ConversationType type,
    String title,
    LocalDateTime updatedAt,
    Message lastMessage
) { }