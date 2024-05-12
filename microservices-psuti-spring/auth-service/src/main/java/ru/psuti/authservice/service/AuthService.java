package ru.psuti.authservice.service;

import ru.psuti.authservice.payload.request.LoginRequest;
import ru.psuti.authservice.payload.response.JwtResponse;
import ru.psuti.authservice.payload.response.LdapResponse;

import java.util.List;

public interface AuthService {
    JwtResponse authenticate(LoginRequest loginRequest);

    List<LdapResponse> getAllUsers();

    LdapResponse getUserByUid(String uid);


}
