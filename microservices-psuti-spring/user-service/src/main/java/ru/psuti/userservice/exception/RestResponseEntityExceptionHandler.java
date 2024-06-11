package ru.psuti.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.psuti.userservice.payload.response.ErrorResponse;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(UserServiceCustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(UserServiceCustomException exception) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorMessage(exception.getMessage())
                .errorCode(exception.getErrorCode())
                .build(), HttpStatus.valueOf(exception.getStatus()));
    }
}
