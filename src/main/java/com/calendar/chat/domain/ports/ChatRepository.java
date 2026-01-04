package com.calendar.chat.domain.ports;

import com.calendar.chat.domain.models.Conversation;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ChatRepository {

    Mono<Conversation> save(Conversation conversation);

    Mono<Conversation> findByParticipantIds(List<String> participantIds);
}
