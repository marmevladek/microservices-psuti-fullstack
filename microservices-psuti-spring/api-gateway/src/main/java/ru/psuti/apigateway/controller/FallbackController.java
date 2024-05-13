package ru.psuti.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/userServiceFallBack")
    public String userServiceFallBack() {
        return "User Service is down!";
    }

    @GetMapping("/authServiceFallBack")
    public String authServiceFallBack() {
        return "Auth Service is down!";
    }

    @GetMapping("/fileServiceFallBack")
    public String fileServiceFallBack() {
        return "File Service is down!";
    }
}
