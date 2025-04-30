package com.dmitryshundrik.knowledgebase.service.scheduler;

import com.dmitryshundrik.knowledgebase.service.core.EntityEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EntityEventSchedulerService {

    private final EntityEventService entityEventService;

    @Scheduled(cron = "${knowledge.base.application.entity-event.update.interval}")
    public void updateEntityEventCacheScheduled() {
        try {
            log.info("Updating entity events cache: {}", "entity events cache");
            entityEventService.processEntityEvents(10);
        } catch (Exception e) {
            log.error("Failed to update entity events cache: {}", e.getMessage(), e);
        }
    }
}
