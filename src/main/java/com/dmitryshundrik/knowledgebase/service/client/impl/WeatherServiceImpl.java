package com.dmitryshundrik.knowledgebase.service.client.impl;

import com.dmitryshundrik.knowledgebase.client.WeatherClient;
import com.dmitryshundrik.knowledgebase.exception.ClientServiceException;
import com.dmitryshundrik.knowledgebase.model.dto.client.openweather.Weather;
import com.dmitryshundrik.knowledgebase.model.dto.client.openweather.WeatherResponse;
import com.dmitryshundrik.knowledgebase.service.client.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

import static com.dmitryshundrik.knowledgebase.exception.ClientServiceException.GETTING_CURRENT_WEATHER_FAIL_MESSAGE;
import static com.dmitryshundrik.knowledgebase.exception.ClientServiceException.WEATHER_RESPONSE_IS_NULL_MESSAGE;
import static com.dmitryshundrik.knowledgebase.util.Constants.UNKNOWN_VALUE;
import static com.dmitryshundrik.knowledgebase.util.Constants.WEATHER_CACHE;

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

    @Override
    @Cacheable(value = WEATHER_CACHE, key = "#root.methodName")
    public Weather getCurrentWeather() {
        return processCurrentWeather();
    }

    @Override
    @CachePut(value = WEATHER_CACHE, key = "'getCurrentWeather'")
    public Weather processCurrentWeather() {
        WeatherResponse weatherResponse = getCurrentWeatherFromClient();
        if (weatherResponse == null) {
            throw new ClientServiceException(WEATHER_RESPONSE_IS_NULL_MESSAGE);
        }

        return Weather.builder()
                .date(LocalDate.now().getDayOfWeek().toString())
                .temp(Optional.ofNullable(weatherResponse.getMain())
                        .map(main -> String.valueOf((int) Math.ceil(main.getTemp())))
                        .orElse(UNKNOWN_VALUE))
                .humidity(Optional.ofNullable(weatherResponse.getMain())
                        .map(main -> String.valueOf(main.getHumidity()))
                        .orElse(UNKNOWN_VALUE))
                .description(Optional.ofNullable(weatherResponse.getWeather())
                        .filter(weather -> weather.length > 0)
                        .map(weather -> weather[0])
                        .map(WeatherResponse.Weather::getDescription)
                        .orElse(UNKNOWN_VALUE))
                .wingSpeed(Optional.ofNullable(weatherResponse.getWind())
                        .map(wind -> String.valueOf(wind.getSpeed()))
                        .orElse(UNKNOWN_VALUE))
                .build();
    }

    private WeatherResponse getCurrentWeatherFromClient() {
        try {
            WeatherResponse responseDto = weatherClient
                    .getCurrentWeather(defaultCity, apiKey, units);
            log.info("Received response: {}", responseDto);
            return responseDto;
        } catch (Exception e) {
            throw new ClientServiceException(GETTING_CURRENT_WEATHER_FAIL_MESSAGE.formatted(e.getMessage()));
        }
    }
}
