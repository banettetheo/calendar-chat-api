package com.calendar.chat.infrastructure.persistence.repositories;

import com.calendar.chat.infrastructure.persistence.models.entities.ConversationEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends ReactiveMongoRepository<ConversationEntity, String> {

}
