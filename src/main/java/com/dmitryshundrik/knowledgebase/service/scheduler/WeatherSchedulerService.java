package com.dmitryshundrik.knowledgebase.service.scheduler;

import com.dmitryshundrik.knowledgebase.service.client.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherSchedulerService {

    private final WeatherService weatherService;

    @Value("${openweather.api.default-city}")
    private String defaultCity;

    @Scheduled(cron = "${openweather.api.update.interval}")
    public void updateWeatherCacheScheduled() {
        try {
            log.info("Updating weather cache for city: {}", defaultCity);
            weatherService.processCurrentWeather();
        } catch (Exception e) {
            log.error("Failed to update weather cache: {}", e.getMessage(), e);
        }
    }
}
