package com.sumdu.edu.ua.ivan.gamestore.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class StoreAPIException extends RuntimeException {

    private final HttpStatus status;
    private final String message;

    public StoreAPIException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
