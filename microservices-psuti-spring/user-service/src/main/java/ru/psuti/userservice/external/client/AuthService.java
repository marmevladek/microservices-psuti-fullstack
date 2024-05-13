package ru.psuti.userservice.external.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.psuti.userservice.exception.UserServiceCustomException;
import ru.psuti.userservice.payload.LdapResponse;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "AUTH-SERVICE/auth")
public interface AuthService {

    @GetMapping("/getUserByUid/{uid}")
    LdapResponse getUserByUid(@PathVariable("uid") String uid);

    default LdapResponse fallback(Exception e) {
        throw new UserServiceCustomException("Auth Service is not available", "UNAVAILABLE", 500);
    }
}
