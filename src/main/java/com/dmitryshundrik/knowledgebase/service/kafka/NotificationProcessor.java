package com.dmitryshundrik.knowledgebase.service.kafka;

import com.dmitryshundrik.knowledgebase.model.entity.core.CurrentEventInfo;
import com.dmitryshundrik.knowledgebase.kafka.KafkaProducer;
import com.dmitryshundrik.knowledgebase.service.core.CurrentEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationProcessor {

    private final KafkaProducer kafkaProducer;

    private final CurrentEventService currentEventService;

    @Scheduled(cron = "0 0 0 * * *")
    public void processEmailNotification() {
        List<CurrentEventInfo> currentEvents = currentEventService.getCurrentNotification(2);
        if (currentEvents != null && !currentEvents.isEmpty()) {
            StringBuilder email = new StringBuilder();
            email.append("Календарь событий:");
            email.append(System.lineSeparator());
            for (CurrentEventInfo currentEventInfo : currentEvents) {
                email.append(currentEventInfo.getDate());
                email.append(currentEventInfo.getDateType());
                email.append(currentEventInfo.getOccupation());
                email.append(" ");
                email.append(currentEventInfo.getPersonNickname()).append(".");
                email.append(System.lineSeparator());
            }
            kafkaProducer.sendMessage("email-notification", email.toString());
        }
    }
}
