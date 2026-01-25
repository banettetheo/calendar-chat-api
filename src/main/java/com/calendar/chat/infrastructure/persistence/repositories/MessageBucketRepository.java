package com.calendar.chat.infrastructure.persistence.repositories;

import com.calendar.chat.infrastructure.persistence.models.entities.MessageBucketEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MessageBucketRepository extends ReactiveMongoRepository<MessageBucketEntity, String> {

    Mono<MessageBucketEntity> findFirstByConversationIdOrderByBucketIndexDesc(String conversationId);

    Mono<MessageBucketEntity> findByConversationIdAndBucketIndex(String conversationId, Integer bucketIndex);
}
