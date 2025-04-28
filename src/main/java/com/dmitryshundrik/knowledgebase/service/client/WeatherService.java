package com.dmitryshundrik.knowledgebase.service.client;

import com.dmitryshundrik.knowledgebase.model.dto.client.openweather.Weather;

public interface WeatherService {

    Weather getCurrentWeather();

    Weather processCurrentWeather();
}
