package com.dmitryshundrik.knowledgebase.model.dto.client.openweather;

import lombok.Builder;

@Builder
public record Weather(
        String date,
        String temp,
        String humidity,
        String description,
        String wingSpeed) {
}
