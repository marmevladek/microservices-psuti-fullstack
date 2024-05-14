package ru.psuti.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.psuti.userservice.exception.UserServiceCustomException;
import ru.psuti.userservice.mapper.HandlingMapper;
import ru.psuti.userservice.model.Handling;
import ru.psuti.userservice.payload.HandlingDto;
import ru.psuti.userservice.payload.ResponseMessage;
import ru.psuti.userservice.payload.response.ResponseHandling;
import ru.psuti.userservice.repository.FileInfoRepository;
import ru.psuti.userservice.repository.HandlingRepository;
import ru.psuti.userservice.service.TeacherService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final HandlingRepository handlingRepository;
    private final FileInfoRepository fileInfoRepository;

    @Override
    public List<ResponseHandling> getHandlingList() {

        List<Handling> handlingList = handlingRepository.findAll();

        return handlingList.stream()
                .map(HandlingMapper::mapToResponseHandling)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseMessage updateHandling(Long id, HandlingDto handlingDto) {

        Handling handling = handlingRepository.findById(id)
                .orElseThrow(
                        () -> new UserServiceCustomException("Handling with id: " + id + " not fount", "NOT_FOUND", 404)
                );

        handling.setDateOfInspection(Instant.now());
        handling.setComment(handlingDto.getComment());
        handling.setStatus(handlingDto.getStatus());

        handlingRepository.save(handling);

        return new ResponseMessage("Successfully updated!");
    }
}
