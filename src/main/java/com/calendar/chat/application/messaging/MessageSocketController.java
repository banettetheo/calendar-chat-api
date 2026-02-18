package com.calendar.chat.application.messaging;

import com.calendar.chat.application.dtos.ChatInputDTO;
import com.calendar.chat.domain.models.Message;
import com.calendar.chat.domain.services.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Controller
public class MessageSocketController {

    private final ChatService chatService;

    public MessageSocketController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("chat.send")
    public Mono<Void> sendMessage(@AuthenticationPrincipal Jwt jwt, ChatInputDTO chatInputDTO) {
        String userId = jwt.getClaimAsString("businessId");
        String username = jwt.getClaimAsString("preferred_username");

        Message message = new Message(null, userId, username, chatInputDTO.receiverId(),
                chatInputDTO.conversationId(), chatInputDTO.content(), LocalDateTime.now());
        return chatService.sendMessage(message);
    }

    @MessageMapping("chat.stream")
    public Flux<Message> streamMessage(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getClaimAsString("businessId");

        return chatService.streamMessages(userId);
    }
}
