package com.calendar.chat.infrastructure.persistence.adapters;

import com.calendar.chat.domain.models.ConversationDetail;
import com.calendar.chat.domain.models.ConversationSummary;
import com.calendar.chat.domain.models.Message;
import com.calendar.chat.domain.ports.ChatRepository;
import com.calendar.chat.infrastructure.persistence.mappers.ConversationMapper;
import com.calendar.chat.infrastructure.persistence.models.dtos.MessageEntity;
import com.calendar.chat.infrastructure.persistence.models.entities.ConversationEntity;
import com.calendar.chat.infrastructure.persistence.models.entities.MessageBucketEntity;
import com.calendar.chat.infrastructure.persistence.repositories.ConversationRepository;
import com.calendar.chat.infrastructure.persistence.repositories.MessageBucketRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public Mono<ConversationDetail> saveWithInitialBucket(List<String> participantIds) {

        ConversationEntity conversationEntityToSave = new ConversationEntity(null, participantIds, null, null);

        return conversationRepository.save(conversationEntityToSave)
                .flatMap(conversationEntity -> {
                    MessageBucketEntity messageBucketEntityToSave = new MessageBucketEntity(
                            null,
                            conversationEntity.getId(),
                            0,
                            new ArrayList<>(),
                            false
                    );

                    ConversationDetail conversationDetail = conversationMapper.toConversationDetail(conversationEntity);

                    return messageBucketRepository.save(messageBucketEntityToSave)
                            .flatMap(messageBucketEntity -> {

                                conversationDetail.setMessages(messageBucketEntity.getMessages().stream().map(conversationMapper::toMessage).toList());

                                return Mono.just(conversationDetail);
                            });
                });
    }

    public Mono<ConversationDetail> findByParticipantIds(List<String> participantIds) {
        return conversationRepository.findByParticipantIds(participantIds)
                .flatMap(conversationEntity -> {
                    ConversationDetail conversationDetail = conversationMapper.toConversationDetail(conversationEntity);

                    return messageBucketRepository.findFirstByConversationId(conversationEntity.getId())
                            .flatMap(messageBucketEntity -> {
                                List<Message> messageList = messageBucketEntity.getMessages().stream().map(conversationMapper::toMessage).toList();

                                conversationDetail.setMessages(messageList);

                                return Mono.just(conversationDetail);
                            });
                });
    }

    public Mono<Void> postMessage(Message message) {
        MessageEntity messageEntity = conversationMapper.toMessageEntity(message);

        return messageBucketRepository.findFirstByConversationId(message.conversationId())
                .flatMap(messageBucketEntity -> {
                    messageBucketEntity.getMessages().add(messageEntity);
                    return messageBucketRepository.save(messageBucketEntity).then();
                });
    }

//    public Flux<ConversationSummary> findConversationByUserId(String userId) {
//        return conversationRepository.fin(userId)
//    }
}
