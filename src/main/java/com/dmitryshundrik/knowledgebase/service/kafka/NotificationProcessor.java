package com.dmitryshundrik.knowledgebase.service.kafka;

import com.dmitryshundrik.knowledgebase.model.entity.core.EntityCurrentEvent;
import com.dmitryshundrik.knowledgebase.kafka.KafkaProducer;
import com.dmitryshundrik.knowledgebase.service.core.EntityNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationProcessor {

    private final KafkaProducer kafkaProducer;

    private final EntityNotificationService entityNotificationService;

    @Scheduled(cron = "${kafka.email-notification.update.interval}")
    public void processEmailNotification() {
        List<EntityCurrentEvent> currentEvents = entityNotificationService.getEntityNotifications(2);
        if (currentEvents != null && !currentEvents.isEmpty()) {
            StringBuilder email = new StringBuilder();
            email.append("Календарь событий:");
            email.append(System.lineSeparator());
            for (EntityCurrentEvent entityCurrentEvent : currentEvents) {
                email.append(entityCurrentEvent.getDate());
                email.append(entityCurrentEvent.getDateType());
                email.append(entityCurrentEvent.getOccupation());
                email.append(" ");
                email.append(entityCurrentEvent.getPersonNickname()).append(".");
                email.append(System.lineSeparator());
            }
            kafkaProducer.sendMessage("email-notification", email.toString());
        }
    }
}
