package ru.psuti.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SendHandlingException extends RuntimeException{
    public SendHandlingException(String message) {
        super(message);
    }
}
