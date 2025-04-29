package com.dmitryshundrik.knowledgebase.service.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LastfmSchedulerService {

    private final LastfmService lastFmService;

    @Scheduled(cron = "${lastfm.api.update.interval}")
    public void updateLastfmCacheScheduled() {
        try {
            log.info("Updating lastfm cache: {}", "lastfm cache");
            lastFmService.processTopArtists();
            lastFmService.processTopAlbums();
        } catch (Exception e) {
            log.error("Failed to update lastfm cache: {}", e.getMessage(), e);
        }
    }
}
