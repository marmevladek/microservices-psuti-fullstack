package ru.psuti.userservice.service;

import org.springframework.web.multipart.MultipartFile;
import ru.psuti.userservice.payload.response.HandlingResponse;
import ru.psuti.userservice.payload.response.MessageResponse;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    MessageResponse sendHandling(String token, MultipartFile file, String uid, String contentType) throws IOException;

    List<HandlingResponse> getHandlingHistory(String uid) throws IOException;

}
