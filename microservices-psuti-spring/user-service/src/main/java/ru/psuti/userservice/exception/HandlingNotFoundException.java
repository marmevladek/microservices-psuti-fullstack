package ru.psuti.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HandlingNotFoundException extends RuntimeException{

    public HandlingNotFoundException(String message) {
        super(message);
    }
}
