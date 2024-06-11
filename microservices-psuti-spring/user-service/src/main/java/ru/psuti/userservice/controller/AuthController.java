package ru.psuti.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import ru.psuti.userservice.payload.request.LoginRequest;
import ru.psuti.userservice.payload.response.MessageResponse;
import ru.psuti.userservice.service.AuthService;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            return new ResponseEntity<>(authService.authenticate(loginRequest), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new MessageResponse("Данные введены неверно. Попробуйте снова."), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("Произошла техническая ошибка. Попробуйте снова."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
