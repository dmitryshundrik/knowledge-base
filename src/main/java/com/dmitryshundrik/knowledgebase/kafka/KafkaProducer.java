package com.dmitryshundrik.knowledgebase.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String message) {
        if (topic == null || message == null) {
            log.error("Topic or message is null");
            throw new IllegalArgumentException("Topic and message must not be null");
        }
        kafkaTemplate.send(topic, message);
        log.info("Message sent to topic {}: {}", topic, message);
    }
}
