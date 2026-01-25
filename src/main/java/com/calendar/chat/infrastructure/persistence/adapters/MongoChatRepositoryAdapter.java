package com.calendar.chat.infrastructure.persistence.adapters;

import com.calendar.chat.domain.models.ConversationDetail;
import com.calendar.chat.domain.models.ConversationSummary;
import com.calendar.chat.domain.models.Message;
import com.calendar.chat.domain.models.MessageBucket;
import com.calendar.chat.domain.ports.ChatRepository;
import com.calendar.chat.infrastructure.persistence.mappers.ConversationMapper;
import com.calendar.chat.infrastructure.persistence.models.dtos.MessageEntity;
import com.calendar.chat.infrastructure.persistence.models.entities.ConversationEntity;
import com.calendar.chat.infrastructure.persistence.models.entities.MessageBucketEntity;
import com.calendar.chat.infrastructure.persistence.repositories.ConversationRepository;
import com.calendar.chat.infrastructure.persistence.repositories.MessageBucketRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
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
                            new ArrayList<>()
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
                .flatMap(this::loadConversationWithMessages);
    }

    public Mono<ConversationDetail> findById(String conversationId) {
        return conversationRepository.findById(conversationId)
                .flatMap(this::loadConversationWithMessages);
    }

    private Mono<ConversationDetail> loadConversationWithMessages(ConversationEntity conversationEntity) {
        return messageBucketRepository.findFirstByConversationIdOrderByBucketIndexDesc(conversationEntity.getId())
                .map(bucket -> buildConversationDetail(conversationEntity, bucket));
    }

    private ConversationDetail buildConversationDetail(ConversationEntity conversationEntity, MessageBucketEntity messageBucketEntity) {
        ConversationDetail conversationDetail = conversationMapper.toConversationDetail(conversationEntity);

        List<Message> messages = messageBucketEntity.getMessages().stream()
                .map(conversationMapper::toMessage)
                .toList();

        conversationDetail.setMessages(messages);
        conversationDetail.setBucketIndex(messageBucketEntity.getBucketIndex());

        return conversationDetail;
    }


    public Mono<Void> postMessage(Message message) {

        return conversationRepository.findById(message.conversationId())
                .flatMap(conversationEntity -> {
                    MessageEntity messageEntity = conversationMapper.toMessageEntity(message);
                    conversationEntity.setLastMessage(messageEntity);

                    return messageBucketRepository.findFirstByConversationIdOrderByBucketIndexDesc(message.conversationId())
                            .flatMap(messageBucketEntity -> {
                                if (messageBucketEntity.getMessages().size() < 50) {
                                    messageBucketEntity.getMessages().add(messageEntity);
                                    return messageBucketRepository.save(messageBucketEntity);
                                } else {

                                    MessageBucketEntity messageBucketEntityToSave = new MessageBucketEntity(
                                            null,
                                            messageBucketEntity.getConversationId(),
                                            messageBucketEntity.getBucketIndex() + 1,
                                            List.of(messageEntity)
                                    );
                                    
                                    return messageBucketRepository.save(messageBucketEntityToSave);
                                }
                            }).then(conversationRepository.save(conversationEntity)).then();
                });
    }

    public Mono<MessageBucket> findBucketByConversationIdAndBucketIndex(String conversationId, Integer bucketIndex) {
        return messageBucketRepository.findByConversationIdAndBucketIndex(conversationId, bucketIndex)
                .map(conversationMapper::toMessageBucket);
    }

    public Flux<ConversationSummary> findUserConversations(String userId) {
        return conversationRepository.findByParticipantIdsContaining(userId).map(conversationMapper::toConversationSummary);
    }
}
