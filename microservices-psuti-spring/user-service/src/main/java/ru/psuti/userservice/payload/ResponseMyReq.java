package ru.psuti.userservice.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMyReq {
    private String message;

    public ResponseMyReq(String message) {
        this.message = message;
    }
}
