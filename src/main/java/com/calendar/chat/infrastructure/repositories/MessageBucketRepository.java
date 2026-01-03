package com.calendar.chat.infrastructure.repositories;

import com.calendar.chat.infrastructure.models.entities.MessageBucketEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageBucketRepository extends ReactiveMongoRepository<MessageBucketEntity, String> {
}
