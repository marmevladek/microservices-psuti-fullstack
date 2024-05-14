package ru.psuti.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.psuti.userservice.exception.UserServiceCustomException;
import ru.psuti.userservice.external.client.AuthService;
import ru.psuti.userservice.external.client.FileService;
import ru.psuti.userservice.mapper.HandlingMapper;
import ru.psuti.userservice.model.FileInfo;
import ru.psuti.userservice.model.Handling;
import ru.psuti.userservice.payload.LdapResponse;
import ru.psuti.userservice.payload.ResponseMessage;
import ru.psuti.userservice.payload.request.RequestFileDelete;
import ru.psuti.userservice.payload.request.RequestHandling;
import ru.psuti.userservice.payload.response.ResponseHandling;
import ru.psuti.userservice.repository.FileInfoRepository;
import ru.psuti.userservice.repository.HandlingRepository;
import ru.psuti.userservice.service.StudentService;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final HandlingRepository handlingRepository;
    private final FileInfoRepository fileInfoRepository;
    private final FileService fileService;
    private final AuthService authService;

    @Override
    public ResponseMessage sendHandling(String token, MultipartFile file, String uid, String contentType) throws IOException {
        log.info("UserServiceImpl | student | sendRequest is called");

        log.info("UserServiceImpl | student | sendRequest | Sending Request");

        log.info("UserServiceImpl | student | sendRequest | Calling Auth Service through FeignClient");

        LdapResponse ldapResponse = authService.getUserByUid(uid);

        log.info("UserServiceImpl | student | sendRequest | Done calling Auth Service through FeignClient");

        log.info("UserServiceImpl | student | sendRequest | Calling File Service through FeignClient");


        String path = "/files/" + uid + "/";
        String name = file.getOriginalFilename();
        byte[] fileBytes = file.getBytes();


        try {
            fileService.uploadFile(token, new RequestHandling(
                    fileBytes,
                    path,
                    name
            ));
            log.info("UserServiceImpl | student | sendRequest | Done calling File Service through FeignClient");
        } catch (StackOverflowError e) {
            log.error("UserServiceImpl | student | sendRequest | Stack Overflow Error : {}", e.getMessage());
        } catch (PatternSyntaxException e) {
            log.error("UserServiceImpl | student | sendRequest | PatternSyntaxException | {}", e.getMessage());
        }

        FileInfo fileInfo = new FileInfo(
                path,
                name
        );


        Handling handling = new Handling(
                "test",
                ldapResponse.getCn(),
                ldapResponse.getSn(),
                Instant.now(),
                null,
                "",
                null,
                uid,
                "teacher-uid",
                fileInfo
        );

        fileInfoRepository.save(fileInfo);
        handlingRepository.save(handling);



        return new ResponseMessage("zbs");

    }

    @Override
    public List<ResponseHandling> getHandlingHistory(String uid) {
        log.info("UserServiceImpl | student | getRequestHistory is called");

        log.info("UserServiceImpl | student | getRequestHistory | Sending Request");

        List<Handling> handlingList = handlingRepository.findAllByStudentUid(uid);

        return handlingList.stream()
                .map(HandlingMapper::mapToResponseHandling)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteHandlingById(String token, Long handlingId) {

        log.info("UserServiceImpl | student | deleteHandlingByUd is called");

        log.info("UserServiceImpl | student | deleteHandlingByUd | Sending Request");

        if (!handlingRepository.existsById(handlingId)) {
            log.info("Im in this loop {}", !handlingRepository.existsById(handlingId));
            throw new UserServiceCustomException("Handling with give id: " + handlingId + " not found", "NOT_FOUND", 404);
        }

        Handling handling = handlingRepository.findById(handlingId)
                .orElseThrow(
                        () -> new UserServiceCustomException("Handling with give id: " + handlingId + " not found", "NOT_FOUND", 404)
                );

        if (!fileInfoRepository.existsById(handling.getFile().getId())) {
            log.info("Im in this loop {}", !fileInfoRepository.existsById(handling.getFile().getId()));
            throw new UserServiceCustomException("File from handling with give id: " + handlingId + " not found", "NOT_FOUND", 404);
        }





        log.info("UserServiceImpl | student | deleteHandlingByUd | Calling File Service through FeignClient");

        fileService.deleteFile(token, new RequestFileDelete(
                handling.getFile().getPath(),
                handling.getFile().getName()
        ));

        log.info("UserServiceImpl | student | deleteHandlingByUd | Done Calling File Service through FeignClient");

        handlingRepository.deleteById(handlingId);
        fileInfoRepository.deleteById(handling.getFile().getId());
    }
}
