package com.calendar.chat.infrastructure.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
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
    
    @Indexed
    private List<String> participantIds;
    
    //private LastMessage lastMessage;
    
    private LocalDateTime updatedAt;
}