package com.dmitryshundrik.knowledgebase.service.client.impl;

import com.dmitryshundrik.knowledgebase.client.WeatherClient;
import com.dmitryshundrik.knowledgebase.exception.WeatherServiceException;
import com.dmitryshundrik.knowledgebase.model.dto.client.openweather.WeatherResponse;
import com.dmitryshundrik.knowledgebase.service.client.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.dmitryshundrik.knowledgebase.util.Constants.GETTING_CURRENT_WEATHER_FAIL_MESSAGE;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherServiceImpl implements WeatherService {

    private final WeatherClient weatherClient;

    @Value("${openweather.api.default-city}")
    private String defaultCity;

    @Value("${openweather.api.key}")
    private String apiKey;

    @Value("${openweather.api.units}")
    private String units;

    private void getCurrentWeatherFromClient() {
        try {
            String city = defaultCity;
            log.info("Requesting weather for city: {}", city);
            WeatherResponse responseDto = weatherClient
                    .getCurrentWeather(defaultCity, apiKey, units);
            log.info("Received response: {}", responseDto);
        } catch (Exception e) {
            log.error("Error while fetching weather data: {}", e.getMessage(), e);
            throw new WeatherServiceException(GETTING_CURRENT_WEATHER_FAIL_MESSAGE.formatted(e.getMessage()));
        }
    }

    @Override
//    @Scheduled(cron = "0 * * * * *")
    public void processCurrentWeather() {
        getCurrentWeatherFromClient();
    }
}
