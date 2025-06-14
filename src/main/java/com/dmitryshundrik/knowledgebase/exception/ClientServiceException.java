package com.dmitryshundrik.knowledgebase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClientServiceException extends ResponseStatusException {

    public static final String WEATHER_RESPONSE_IS_NULL_MESSAGE = "Weather response is null";
    public static final String GETTING_CURRENT_WEATHER_FAIL_MESSAGE = "Failed to fetch weather data: %s";

    public ClientServiceException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
