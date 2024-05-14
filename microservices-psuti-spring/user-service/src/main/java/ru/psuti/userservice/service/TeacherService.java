package ru.psuti.userservice.service;


import ru.psuti.userservice.payload.HandlingDto;
import ru.psuti.userservice.payload.ResponseMessage;
import ru.psuti.userservice.payload.response.ResponseHandling;

import java.util.List;

public interface TeacherService {
    List<ResponseHandling> getHandlingList();

    ResponseMessage updateHandling(Long id, HandlingDto handlingDto);
}
