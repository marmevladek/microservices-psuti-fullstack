package ru.psuti.userservice.service;

import org.springframework.web.multipart.MultipartFile;
import ru.psuti.userservice.payload.ResponseMessage;

import java.io.IOException;

public interface StudentService {
    ResponseMessage sendRequest(MultipartFile file, String uid, String token, String contentType) throws IOException;
}
