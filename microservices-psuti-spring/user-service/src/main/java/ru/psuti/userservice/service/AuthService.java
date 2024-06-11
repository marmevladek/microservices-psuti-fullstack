package ru.psuti.userservice.service;

import ru.psuti.userservice.payload.request.LoginRequest;
import ru.psuti.userservice.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse authenticate(LoginRequest loginRequest);
}
