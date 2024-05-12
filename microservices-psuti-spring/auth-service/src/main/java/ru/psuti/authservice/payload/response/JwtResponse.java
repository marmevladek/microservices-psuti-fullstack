package ru.psuti.authservice.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String username;
    private String uid;
    private String role;

    private String type = "Bearer";

    public JwtResponse(String token, String username, String uid, String role) {
        this.token = token;
        this.username = username;
        this.uid = uid;
        this.role = role;
    }
}
