package com.dmitryshundrik.knowledgebase.model.dto.client.openweather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherResponse {
    private String name;

    @JsonProperty("main")
    private Main main;

    @JsonProperty("weather")
    private Weather[] weather;

    @JsonProperty("wind")
    private Wind wind;

    @Data
    public static class Main {

        private double temp;

        private int humidity;

        private int pressure;

    }

    @Data
    public static class Weather {

        private String description;

        private String icon;

    }

    @Data
    public static class Wind {

        private double speed;

        private int deg;

    }
}