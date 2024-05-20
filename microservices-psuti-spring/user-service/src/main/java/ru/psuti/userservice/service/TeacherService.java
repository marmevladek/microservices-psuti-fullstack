package ru.psuti.userservice.service;


import org.springframework.web.multipart.MultipartFile;
import ru.psuti.userservice.payload.HandlingDto;
import ru.psuti.userservice.payload.ResponseMessage;
import ru.psuti.userservice.payload.response.ResponseHandling;

import java.io.IOException;
import java.util.List;

public interface TeacherService {
    List<ResponseHandling> getHandlingList();

    HandlingDto getHandlingById(Long id);

    ResponseMessage updateHandling(String token, Long id, MultipartFile file, String comment, Boolean status) throws IOException;
}
