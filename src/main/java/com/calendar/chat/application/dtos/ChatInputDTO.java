package com.calendar.chat.application.dtos;

public record ChatInputDTO(
        String senderUsername,
        String receiverId,
        String conversationId,
        String content
) {}