package com.example.wallet.exception.type;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadRequestException extends ResponseStatusException {
    public BadRequestException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}