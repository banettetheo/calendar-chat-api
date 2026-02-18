package com.calendar.chat.application.dtos;

import com.calendar.chat.domain.models.ConversationType;
import java.util.List;

public record ConversationDetailResponse(
        String id,
        List<String> participantIds,
        String title,
        ConversationType type,
        List<MessageResponse> messages,
        Integer bucketIndex) {
}
