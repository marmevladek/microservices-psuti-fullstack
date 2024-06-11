package ru.psuti.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileWrongFormatException extends RuntimeException{

    public FileWrongFormatException(String message) {
        super(message);
    }
}
