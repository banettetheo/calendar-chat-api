package com.calendar.chat.domain.services;

import com.calendar.chat.domain.models.ConversationDetail;
import com.calendar.chat.domain.models.ConversationSummary;
import com.calendar.chat.domain.models.Message;
import com.calendar.chat.domain.models.MessageBucket;
import com.calendar.chat.domain.ports.ChatRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.List;

public class ChatService {

    private final ChatRepository chatRepository;
    private final Sinks.Many<Message> sink = Sinks.many().multicast().directBestEffort();

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Mono<ConversationDetail> readOrCreateConversation(List<String> participantIds) {
        return chatRepository.findByParticipantIds(participantIds)
                .switchIfEmpty(Mono.defer(() -> chatRepository.saveWithInitialBucket(participantIds)));
    }

    // todo : gestion des erreurs
    public Mono<Void> sendMessage(Message message) {
        sink.tryEmitNext(message);
        return chatRepository.postMessage(message);
    }

    public Flux<Message> streamMessages(String userId) {
        return sink.asFlux()
                .filter(msg -> msg.receiverId().equals(userId));
    }

    public Mono<MessageBucket> readPreviousMessages(String conversationId, Integer bucketIndex) {
        return chatRepository.findBucketByConversationIdAndBucketIndex(conversationId, bucketIndex);
    }

    public Mono<ConversationDetail> readConversationById(String conversationId) {
        return chatRepository.findById(conversationId);
    }

    public Flux<ConversationSummary> readConversations(String userId) {
        return chatRepository.findUserConversations(userId);
    }
}
