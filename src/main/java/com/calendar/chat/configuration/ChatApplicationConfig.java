package com.calendar.chat.configuration;

import com.calendar.chat.domain.ports.ChatRepository;
import com.calendar.chat.domain.services.ChatService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories( basePackages = "com.calendar.chat.infrastructure.persistence.repositories")
public class ChatApplicationConfig {

    @Bean
    public ChatService chatService(ChatRepository chatRepository) {
        return new ChatService(chatRepository);
    }
}
