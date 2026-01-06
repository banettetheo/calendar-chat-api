package com.calendar.chat.infrastructure.persistence.repositories;

import com.calendar.chat.infrastructure.persistence.models.entities.ConversationEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface ConversationRepository extends ReactiveMongoRepository<ConversationEntity, String> {

    Mono<ConversationEntity> findByParticipantIds(List<String> participantIds);

    Mono<ConversationEntity> findByParticipantIdsContaining(String userId);
}
