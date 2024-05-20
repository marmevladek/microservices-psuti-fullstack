package ru.psuti.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.psuti.userservice.exception.UserServiceCustomException;
import ru.psuti.userservice.external.client.FileService;
import ru.psuti.userservice.mapper.HandlingMapper;
import ru.psuti.userservice.model.Handling;
import ru.psuti.userservice.payload.FileDto;
import ru.psuti.userservice.payload.HandlingDto;
import ru.psuti.userservice.payload.ResponseMessage;
import ru.psuti.userservice.payload.request.RequestHandling;
import ru.psuti.userservice.payload.response.ResponseHandling;
import ru.psuti.userservice.repository.HandlingRepository;
import ru.psuti.userservice.service.TeacherService;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final HandlingRepository handlingRepository;
    private final FileService fileService;

    @Override
    public List<ResponseHandling> getHandlingList() {

        List<Handling> handlingList = handlingRepository.findAll();

        return handlingList.stream()
                .map(HandlingMapper::mapToResponseHandling)
                .collect(Collectors.toList());
    }

    @Override
    public HandlingDto getHandlingById(Long id) {
        Handling handling = handlingRepository.findById(id)
                .orElseThrow(
                        () -> new UserServiceCustomException("Handling with id: " + id + " not found", "NOT_FOUND", 404)
                );

        return HandlingMapper.mapToHandlingDto(handling);
    }

    @Override
    public ResponseMessage updateHandling(String token, Long id, MultipartFile file, String comment, Boolean status) throws IOException {
        Handling handling = handlingRepository.findById(id)
                .orElseThrow(
                        () -> new UserServiceCustomException("Handling with id: " + id + " not found", "NOT_FOUND", 404)
                );

        String path = handling.getFile().getPath();
        String name = handling.getFile().getName();

        handling.setComment(comment);
        handling.setStatus(status);
        handling.setDateOfInspection(Instant.now());

        byte[] fileBytes = file.getBytes();


        fileService.deleteFileById(token, new FileDto(
                path,
                name
        ));

        fileService.uploadFile(token, new RequestHandling(
                fileBytes,
                path,
                "upd_" + file.getOriginalFilename()
        ));

        handling.getFile().setName("upd_"+file.getOriginalFilename());
        log.info("upload file done");
        handlingRepository.save(handling);

        return new ResponseMessage("File is deleted successfully!");
    }

}
