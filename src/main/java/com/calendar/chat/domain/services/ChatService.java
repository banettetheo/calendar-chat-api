package com.calendar.chat.domain.services;

import com.calendar.chat.domain.models.Conversation;
import com.calendar.chat.domain.ports.ChatRepository;
import reactor.core.publisher.Mono;

import java.util.List;

public class ChatService {

    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Mono<Conversation> readOrCreateConversation(List<String> participantIds) {
        return chatRepository.findByParticipantIds(participantIds)
                .switchIfEmpty(Mono.defer(() -> {
                    Conversation conversation = new Conversation(
                            null,
                            participantIds,
                            null,
                            null,
                            null,
                            false);
                    return chatRepository.save(conversation);
                }));
    }
}
