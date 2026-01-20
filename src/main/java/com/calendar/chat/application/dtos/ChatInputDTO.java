package com.calendar.chat.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record ChatInputDTO(
        String receiverId,
        String content
) {}