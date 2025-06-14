package com.dmitryshundrik.knowledgebase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MessageBrokerException extends ResponseStatusException {

    public static final String TOPIC_OR_MESSAGE_ARE_NULL_MESSAGE = "Topic and message must not be null";

    public MessageBrokerException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
