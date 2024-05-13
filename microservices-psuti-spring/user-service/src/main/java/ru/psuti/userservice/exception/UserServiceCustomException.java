package ru.psuti.userservice.exception;

import lombok.Data;

@Data
public class UserServiceCustomException extends RuntimeException{

    private String errorCode;
    private int status;

    public UserServiceCustomException(String message, String errorCode, int status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}
