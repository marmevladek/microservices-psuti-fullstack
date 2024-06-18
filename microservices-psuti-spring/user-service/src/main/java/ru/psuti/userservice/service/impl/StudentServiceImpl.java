package ru.psuti.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.psuti.userservice.exception.*;
import ru.psuti.userservice.external.client.FileService;
import ru.psuti.userservice.mapper.HandlingMapper;
import ru.psuti.userservice.model.FileInfo;
import ru.psuti.userservice.model.Handling;
import ru.psuti.userservice.payload.request.FileRequest;
import ru.psuti.userservice.payload.response.HandlingResponse;
import ru.psuti.userservice.payload.response.MessageResponse;
import ru.psuti.userservice.payload.response.UserByUidResponse;
import ru.psuti.userservice.repository.FileInfoRepository;
import ru.psuti.userservice.repository.HandlingRepository;
import ru.psuti.userservice.service.StudentService;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Log4j2
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final HandlingRepository handlingRepository;
    private final FileInfoRepository fileInfoRepository;
    private final FileService fileService;
    private final LdapTemplate ldapTemplate;

    private static final String BASE_DN = "ou=users,ou=system";

    @Override
    public MessageResponse sendHandling(String token, MultipartFile file, String uid, String contentType) throws IOException {
        if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".docx")) {
            throw new FileWrongFormatException("Файл должен иметь формат .docx");
        }

        log.info("UserServiceImpl | student | sendRequest is called");

        log.info("UserServiceImpl | student | sendRequest | Sending Request");

        log.info("UserServiceImpl | student | sendRequest | Calling Auth Service through FeignClient");

        UserByUidResponse userByUidResponse = getUserByUid(uid);
        log.info("UserServiceImpl | student | sendRequest | Done calling Auth Service through FeignClient");

        log.info("UserServiceImpl | student | sendRequest | Calling File Service through FeignClient");

        String path = "/files/" + uid + "/";
        String name = file.getOriginalFilename();
        byte[] fileBytes = file.getBytes();
        try {
            fileService.uploadFile(token, new FileRequest(
                    fileBytes,
                    path,
                    name
            ));
        }  catch (RuntimeException e) {
            throw new UserServiceCustomException(e.getMessage(), "SERVICE_UNAVAILABLE", 503);
        }catch (StackOverflowError e) {
            throw new CallingFileServiceException(e.getMessage());
        }

        FileInfo fileInfo = new FileInfo(
                path,
                name
        );

        Long studentUid = (long) Integer.parseInt(uid);
        Handling handling = new Handling(
                studentUid,
                7357495674L,
                "",
                null,
                LocalDateTime.now(),
                null,
                fileInfo
        );

        fileInfoRepository.save(fileInfo);
        handlingRepository.save(handling);



        return new MessageResponse("Ваше обращение отправлено. Историю обращений вы можете посмотреть на верхней панеле в разделе «история».");


    }

    @Override
    public List<HandlingResponse> getHandlingHistory(Long uid) {
        log.info("UserServiceImpl | student | getRequestHistory is called");

        log.info("UserServiceImpl | student | getRequestHistory | Sending Request");

        List<UserByUidResponse> userDetails = ldapTemplate.search(BASE_DN, "(uid=" + uid + ")",
                (AttributesMapper<UserByUidResponse>) attr ->
                    new UserByUidResponse());

        if (userDetails.isEmpty()) throw new UserNotFoundException("Невозможно загрузить историю обращений, так как пользователя с uid=" + uid + " не найдено!");


        List<Handling> handlingList = handlingRepository.findAllByStudentUid(uid);



        return handlingList.stream()
                .map(HandlingMapper::mapToResponseHandling)
                .toList();
    }

    private UserByUidResponse getUserByUid(String uid) {
        List<UserByUidResponse>  userDetails = ldapTemplate.search(BASE_DN, "(uid=" + uid + ")",
                (AttributesMapper<UserByUidResponse>) attr ->
                    new UserByUidResponse(
                            attr.get("cn").get().toString(),
                            attr.get("sn").get().toString()
                    )
        );

        if (!userDetails.isEmpty()) return userDetails.get(0);

        throw new UserNotFoundException("Пользователь с uid=" + uid + " не найден");
    }
}
