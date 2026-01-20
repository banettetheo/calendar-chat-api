package com.calendar.chat.application.messaging;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class MessageSocketController {

    public MessageSocketController() {

    }

    @MessageMapping("chat.send")
    public Mono<Void> handleSendMessage(String message) {
        System.out.println("Received message: " + message);
        return Mono.empty();
    }

}
