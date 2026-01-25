package com.calendar.chat.domain.ports;

import com.calendar.chat.domain.models.ConversationDetail;
import com.calendar.chat.domain.models.ConversationSummary;
import com.calendar.chat.domain.models.Message;
import com.calendar.chat.domain.models.MessageBucket;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ChatRepository {

    Mono<ConversationDetail> saveWithInitialBucket(List<String> participantIds);

    Mono<ConversationDetail> findByParticipantIds(List<String> participantIds);

    Mono<Void> postMessage(Message message);

    Mono<MessageBucket> findBucketByConversationIdAndBucketIndex(String conversationId, Integer bucketIndex);

    Mono<ConversationDetail> findById(String conversationId);

    Flux<ConversationSummary> findUserConversations(String userId);
}
