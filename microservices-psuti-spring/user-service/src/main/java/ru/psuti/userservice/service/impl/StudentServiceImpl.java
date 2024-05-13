package ru.psuti.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.psuti.userservice.external.client.AuthService;
import ru.psuti.userservice.external.client.FileService;
import ru.psuti.userservice.model.FileInfo;
import ru.psuti.userservice.model.Request;
import ru.psuti.userservice.payload.LdapResponse;
import ru.psuti.userservice.payload.ResponseMessage;
import ru.psuti.userservice.payload.request.RequestUpload;
import ru.psuti.userservice.repository.FileInfoRepository;
import ru.psuti.userservice.repository.RequestRepository;
import ru.psuti.userservice.service.StudentService;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.regex.PatternSyntaxException;

@Service
@Log4j2
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final RequestRepository requestRepository;
    private final FileInfoRepository fileInfoRepository;
    private final FileService fileService;
    private final AuthService authService;

    @Override
    public ResponseMessage sendRequest(MultipartFile file, String uid, String token, String contentType) throws IOException {
        log.info("UserServiceImpl | student | sendRequest is called");

        log.info("UserServiceImpl | student | sendRequest | Sending Request");

        log.info("UserServiceImpl | student | sendRequest | Calling Auth Service through FeignClient");

        LdapResponse ldapResponse = authService.getUserByUid(uid);

        log.info("UserServiceImpl | student | sendRequest | Done calling Auth Service through FeignClient");

        log.info("UserServiceImpl | student | sendRequest | Calling File Service through FeignClient");


        String path = "/images/" + uid + "/";
        String name = file.getOriginalFilename();
        byte[] fileBytes = file.getBytes();


        try {
            fileService.uploadFile(token, new RequestUpload(
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



        Request request = new Request(
                "test",
                ldapResponse.getCn(),
                ldapResponse.getSn(),
                Instant.now(),
                null,
                "",
                null
        );

        FileInfo fileInfo = new FileInfo(
                path,
                name,
                request
        );

        requestRepository.save(request);
        fileInfoRepository.save(fileInfo);


        return new ResponseMessage("zbs");

    }
}
