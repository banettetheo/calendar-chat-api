package com.calendar.chat.infrastructure.persistence.adapters;

import com.calendar.chat.domain.models.Conversation;
import com.calendar.chat.domain.ports.ChatRepository;
import com.calendar.chat.infrastructure.persistence.mappers.ConversationMapper;
import com.calendar.chat.infrastructure.persistence.models.entities.ConversationEntity;
import com.calendar.chat.infrastructure.persistence.repositories.ConversationRepository;
import com.calendar.chat.infrastructure.persistence.repositories.MessageBucketRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class MongoChatRepositoryAdapter implements ChatRepository {

    private final ConversationRepository conversationRepository;
    private final MessageBucketRepository messageBucketRepository;
    private final ConversationMapper conversationMapper;

    public MongoChatRepositoryAdapter(ConversationRepository conversationRepository, MessageBucketRepository messageBucketRepository, ConversationMapper conversationMapper) {
        this.conversationRepository = conversationRepository;
        this.messageBucketRepository = messageBucketRepository;
        this.conversationMapper = conversationMapper;
    }

    public Mono<Conversation> save(Conversation conversation) {
        return conversationRepository.save(conversationMapper.toConversationEntity(conversation))
                .map(conversationMapper::toConversation);
    }

    public Mono<Conversation> findByParticipantIds(List<String> participantIds) {
        return conversationRepository.findByParticipantIds(participantIds)
                .map(conversationMapper::toConversation);
    }
}
