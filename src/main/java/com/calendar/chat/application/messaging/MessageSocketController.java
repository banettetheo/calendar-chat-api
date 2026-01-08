package com.calendar.chat.application.messaging;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class MessageSocketController {

    public MessageSocketController() {

    }

    @MessageMapping("chat.send.{conversationId}")
    public Mono<Void> handleSendMessage(@DestinationVariable String conversationId, String message) {
        System.out.println("Received message: " + message + " in conversation: " + conversationId);
        return Mono.empty();
    }

}
