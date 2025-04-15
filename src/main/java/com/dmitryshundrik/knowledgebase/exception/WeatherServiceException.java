package com.dmitryshundrik.knowledgebase.exception;

public class WeatherServiceException extends RuntimeException {

    public WeatherServiceException(String message) {
        super(message);
    }
}
