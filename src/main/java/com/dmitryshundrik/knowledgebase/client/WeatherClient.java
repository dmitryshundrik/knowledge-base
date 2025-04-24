package com.dmitryshundrik.knowledgebase.client;

import com.dmitryshundrik.knowledgebase.model.dto.client.openweather.WeatherResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "weather", url = "${openweather.api.url}")
public interface WeatherClient {

    @GetMapping
    WeatherResponse getCurrentWeather(@RequestParam("q") String city,
                                      @RequestParam("appid") String apiKey,
                                      @RequestParam("units") String units);
}
