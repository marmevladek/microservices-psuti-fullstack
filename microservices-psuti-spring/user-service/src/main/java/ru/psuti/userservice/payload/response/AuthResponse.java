package ru.psuti.userservice.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse {
    private String uid;
    private List<String> role;
    private String token;
    private String type = "Bearer";

    public AuthResponse(String uid, List<String> role, String token) {
        this.uid = uid;
        this.role = role;
        this.token = token;

    }
}
