package ru.psuti.userservice.external.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.psuti.userservice.exception.UserServiceCustomException;
import ru.psuti.userservice.dto.FileDto;
import ru.psuti.userservice.payload.request.FileRequest;


@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "FILE-SERVICE/files")
public interface FileService {

//    @PostMapping(value = "/upload")
//    void uploadFile(@RequestHeader("Authorization") String token,
//                    @RequestBody RequestHandling requestUpload);

    @PostMapping(value = "/upload")
    void uploadFile(@RequestHeader("Authorization") String token,
                                               @RequestBody FileRequest fileRequest);

    @GetMapping(value = "/files")
    FileDto getListFiles(@RequestBody FileDto fileDto);

    @DeleteMapping(value = "/delete")
    void deleteFileById(@RequestHeader("Authorization") String token,
                                                   @RequestBody FileDto fileDto);

    @GetMapping("/files/download")
    void downloadFile(@RequestHeader("Authorization") String token,
                                          @RequestParam("path") String path,
                                          @RequestParam("name") String name);

    default void fallback(Exception e) {
        throw new UserServiceCustomException("Файловый сервис временно недоступен, попробуйте позже.", "SERVICE_UNAVAILABLE", 503);
    }
}
