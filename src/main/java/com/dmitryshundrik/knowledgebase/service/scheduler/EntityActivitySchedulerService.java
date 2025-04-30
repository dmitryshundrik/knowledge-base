package com.dmitryshundrik.knowledgebase.service.scheduler;

import com.dmitryshundrik.knowledgebase.service.core.EntityActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EntityActivitySchedulerService {

    private final EntityActivityService entityActivityService;

    @Scheduled(cron = "${knowledge.base.application.entity-activity.update.interval}")
    public void updateEntityActivityCacheScheduled() {
        try {
            log.info("Updating entity activity cache: {}", "entity activity cache");
            entityActivityService.processEntityActivities();
        } catch (Exception e) {
            log.error("Failed to update entity activity cache: {}", e.getMessage(), e);
        }
    }

}
