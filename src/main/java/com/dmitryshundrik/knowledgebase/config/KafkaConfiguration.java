package com.dmitryshundrik.knowledgebase.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {

    @Bean
    public NewTopic topic() {
        return new NewTopic("email-notification", 2, (short) 1);
    }
}
