package ru.psuti.userservice.service;

import org.springframework.web.multipart.MultipartFile;
import ru.psuti.userservice.payload.ResponseMessage;
import ru.psuti.userservice.payload.response.ResponseHandling;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    ResponseMessage sendHandling(String token, MultipartFile file, String uid, String contentType) throws IOException;

    List<ResponseHandling> getHandlingHistory(String uid) throws IOException;

    void deleteHandlingById(String token, Long requestId);
}
