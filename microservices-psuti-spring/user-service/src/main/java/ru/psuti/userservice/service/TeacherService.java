package ru.psuti.userservice.service;


import org.springframework.web.multipart.MultipartFile;
import ru.psuti.userservice.dto.HandlingDto;
import ru.psuti.userservice.payload.response.HandlingByIdResponse;
import ru.psuti.userservice.payload.response.HandlingResponse;
import ru.psuti.userservice.payload.response.MessageResponse;

import java.io.IOException;
import java.util.List;

public interface TeacherService {
    List<HandlingResponse> getHandlingList(Long teacherUid);

    HandlingByIdResponse getHandlingById(Long id);

    MessageResponse updateHandling(String token, Long id, /*MultipartFile file,*/ String comment, Boolean status) throws IOException;
}
