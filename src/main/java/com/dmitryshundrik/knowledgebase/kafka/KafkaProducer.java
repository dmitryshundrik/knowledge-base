package com.dmitryshundrik.knowledgebase.kafka;

import com.dmitryshundrik.knowledgebase.exception.MessageBrokerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.dmitryshundrik.knowledgebase.exception.MessageBrokerException.TOPIC_OR_MESSAGE_ARE_NULL_MESSAGE;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String message) {
        if (topic == null || message == null) {
            throw new MessageBrokerException(TOPIC_OR_MESSAGE_ARE_NULL_MESSAGE);
        }
        kafkaTemplate.send(topic, message);
        log.info("Message sent to topic {}: {}", topic, message);
    }
}
