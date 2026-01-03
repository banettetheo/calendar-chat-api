package com.calendar.chat.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories( basePackages = "com.calendar.chat.infrastructure.repositories")
public class ChatApplicationConfig {
}
