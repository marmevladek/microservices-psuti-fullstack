package ru.psuti.userservice.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String cn; //username
    private String userPassword;
}
