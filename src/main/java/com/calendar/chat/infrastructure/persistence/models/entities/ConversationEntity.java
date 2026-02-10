package com.calendar.chat.infrastructure.persistence.models.entities;

import com.calendar.chat.domain.models.ConversationType;
import com.calendar.chat.infrastructure.persistence.models.dtos.MessageEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "conversations")
@Getter
@Setter
@AllArgsConstructor
public class ConversationEntity {
    @Id
    private String id;

    private String title;

    private ConversationType type;
    
    @Indexed
    private List<String> participantIds;
    
    private MessageEntity lastMessage;

    private LocalDateTime updatedAt;
}