package com.calendar.chat.infrastructure.persistence.models.dtos;

import java.time.LocalDateTime;

public record LastMessage(
    String text,
    String senderId,
    LocalDateTime timestamp,
    boolean isRead
) {}