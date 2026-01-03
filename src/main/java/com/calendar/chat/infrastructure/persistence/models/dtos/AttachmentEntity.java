package com.calendar.chat.infrastructure.persistence.models.dtos;

public record AttachmentEntity(
        String url,
        String fileName,
        Long fileSize,
        String mimeType
) {}