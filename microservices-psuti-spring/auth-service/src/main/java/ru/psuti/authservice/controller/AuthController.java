package ru.psuti.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.psuti.authservice.payload.request.LoginRequest;
import ru.psuti.authservice.payload.response.LdapResponse;
import ru.psuti.authservice.service.AuthService;

import java.util.List;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticate(loginRequest));
    }

    @GetMapping("/")
    public String index() {
        return "Welcome to the home page";
    }

    @GetMapping("/getUserDetails")
    public String getUserDetails(Authentication authentication) {
         UserDetails userDetails = (UserDetails) authentication.getPrincipal();

         //access user details
        String username = userDetails.getUsername();
        boolean accNonExpired = userDetails.isAccountNonExpired();

        return "UserDetails: " + username + "\n Account non expired: " + accNonExpired;
    }

    @GetMapping("/getAllUsers")
    public List<LdapResponse> getAllUsers() {
        return authService.getAllUsers();
    }

    @GetMapping("/getUserByUid/{uid}")
    public LdapResponse getUserByUid(@PathVariable("uid") String uid) {
        System.out.println(authService.getUserByUid(uid));
        return authService.getUserByUid(uid);
    }
}
