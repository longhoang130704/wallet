package com.example.wallet.exception.type;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CookieCreatedException extends ResponseStatusException {
    public CookieCreatedException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
