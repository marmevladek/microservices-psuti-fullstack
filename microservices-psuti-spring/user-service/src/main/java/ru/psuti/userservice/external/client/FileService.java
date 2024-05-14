package ru.psuti.userservice.external.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.psuti.userservice.exception.UserServiceCustomException;
import ru.psuti.userservice.payload.request.RequestFileDelete;
import ru.psuti.userservice.payload.request.RequestHandling;


@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "FILE-SERVICE/files")
public interface FileService {

    @PostMapping(value = "/upload")
    void uploadFile(@RequestHeader("Authorization") String token, @RequestBody RequestHandling requestUpload);

    @DeleteMapping(value = "/delete")
    void deleteFile(@RequestHeader("Authorization") String token, @RequestBody RequestFileDelete requestFileDelete);


    default void fallback(Exception e) {
        throw new UserServiceCustomException("File Service is not available", "UNAVAILABLE", 500);
    }
}
