package com.dmitryshundrik.knowledgebase.client;

import com.dmitryshundrik.knowledgebase.model.dto.client.WeatherResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "gismeteo", url = "${gismeteo.api.url}")
public interface GismeteoClient {

    @GetMapping("/weather/current/{cityId}/")
    WeatherResponseDto getCurrentWeather(@PathVariable("cityId") String cityId,
                                         @RequestHeader("X-Gismeteo-Token") String token);
}
