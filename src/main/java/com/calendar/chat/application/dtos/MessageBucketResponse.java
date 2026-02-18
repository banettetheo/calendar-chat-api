package com.calendar.chat.application.dtos;

import java.util.List;

public record MessageBucketResponse(
        Integer bucketIndex,
        List<MessageResponse> messages) {
}
