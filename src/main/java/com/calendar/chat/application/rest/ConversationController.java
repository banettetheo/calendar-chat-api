package com.calendar.chat.application.rest;

import com.calendar.chat.application.dtos.ConversationDetailResponse;
import com.calendar.chat.application.dtos.ConversationSummaryResponse;
import com.calendar.chat.application.dtos.MessageBucketResponse;
import com.calendar.chat.application.mappers.ChatResponseMapper;
import com.calendar.chat.domain.services.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/conversations")
public class ConversationController {

    private final ChatService chatService;
    private final ChatResponseMapper chatResponseMapper;

    public ConversationController(ChatService chatService, ChatResponseMapper chatResponseMapper) {
        this.chatService = chatService;
        this.chatResponseMapper = chatResponseMapper;
    }

    @GetMapping
    public Mono<ResponseEntity<ConversationDetailResponse>> getConversation(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam String friendId) {
        String userId = jwt.getClaimAsString("businessId");

        return chatService.readOrCreateConversation(List.of(userId, friendId))
                .map(chatResponseMapper::toDetailResponse)
                .map(ResponseEntity::ok);
    }

    @GetMapping("{conversationId}/loadMessages")
    public Mono<ResponseEntity<MessageBucketResponse>> readPreviousMessages(@PathVariable String conversationId,
            @RequestParam Integer bucketIndex) {
        return chatService.readPreviousMessages(conversationId, bucketIndex)
                .map(chatResponseMapper::toBucketResponse)
                .map(ResponseEntity::ok);
    }

    @GetMapping("{conversationId}")
    public Mono<ResponseEntity<ConversationDetailResponse>> getConversationById(@PathVariable String conversationId) {
        return chatService.readConversationById(conversationId)
                .map(chatResponseMapper::toDetailResponse)
                .map(ResponseEntity::ok);
    }

    @GetMapping("all")
    public ResponseEntity<Flux<ConversationSummaryResponse>> getConversations(
            @AuthenticationPrincipal Jwt jwt) {

        String userId = jwt.getClaimAsString("businessId");
        Flux<ConversationSummaryResponse> summaries = chatService.readConversations(userId)
                .map(chatResponseMapper::toSummaryResponse);
        return ResponseEntity.ok().body(summaries);
    }
}
