package com.calendar.chat.infrastructure.persistence.mappers;

import com.calendar.chat.domain.models.Conversation;
import com.calendar.chat.infrastructure.persistence.models.entities.ConversationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConversationMapper {

    ConversationEntity toConversationEntity(Conversation conversation);

    Conversation toConversation(ConversationEntity conversationEntity);
}
