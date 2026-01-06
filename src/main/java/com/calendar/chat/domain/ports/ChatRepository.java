package com.calendar.chat.domain.ports;

import com.calendar.chat.domain.models.ConversationDetail;
import com.calendar.chat.domain.models.ConversationSummary;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ChatRepository {

    Mono<ConversationDetail> saveWithInitialBucket(List<String> participantIds);

    Mono<ConversationDetail> findByParticipantIds(List<String> participantIds);
}
