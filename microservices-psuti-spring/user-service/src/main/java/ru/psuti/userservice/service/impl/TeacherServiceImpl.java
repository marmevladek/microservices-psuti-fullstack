package ru.psuti.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.psuti.userservice.exception.CallingFileServiceException;
import ru.psuti.userservice.exception.HandlingNotFoundException;
import ru.psuti.userservice.external.client.FileService;
import ru.psuti.userservice.mapper.HandlingMapper;
import ru.psuti.userservice.model.Handling;
import ru.psuti.userservice.dto.FileDto;
import ru.psuti.userservice.dto.HandlingDto;
import ru.psuti.userservice.payload.request.FileRequest;
import ru.psuti.userservice.payload.response.HandlingByIdResponse;
import ru.psuti.userservice.payload.response.HandlingResponse;
import ru.psuti.userservice.payload.response.MessageResponse;
import ru.psuti.userservice.payload.response.UserByUidResponse;
import ru.psuti.userservice.repository.HandlingRepository;
import ru.psuti.userservice.service.TeacherService;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final HandlingRepository handlingRepository;
    private final FileService fileService;
    private final LdapTemplate ldapTemplate;

    private static final String BASE_DN = "ou=users,ou=system";

    @Override
    public List<HandlingResponse> getHandlingList(Long teacherUid) {

        List<Handling> handlingList = handlingRepository.findAllByTeacherUid(teacherUid);


        return handlingList.stream()
                .map(HandlingMapper::mapToResponseHandling)
                .collect(Collectors.toList());
    }

    @Override
    public HandlingByIdResponse getHandlingById(Long id) {
        Handling handling = handlingRepository.findById(id)
                .orElseThrow(
                        () -> new HandlingNotFoundException("Обращение с id=" + id + " не найдено!")
                );

        Long studentUid = handling.getStudentUid();

        List<UserByUidResponse> userDetails = ldapTemplate.search(BASE_DN, "(uid=" + studentUid + ")",
                (AttributesMapper<UserByUidResponse>) user ->
                    new UserByUidResponse(
                            user.get("cn").get().toString(),
                            user.get("sn").get().toString()
                    )
                );

        HandlingResponse handlingResponse = HandlingMapper.mapToResponseHandling(handling);
        String studentName = userDetails.get(0).getSn();
        String studentGroup = "ИСТ-01";

        return new HandlingByIdResponse(
                handlingResponse,
                studentGroup,
                studentName
        );

    }

    @Override
    public MessageResponse updateHandling(String token, Long id, /*MultipartFile file,*/ String comment, Boolean status) throws IOException {
        Handling handling = handlingRepository.findById(id)
                .orElseThrow(
                        () -> new HandlingNotFoundException("Обращение с id=" + id + " не найдено!")
                );

        String path = handling.getFile().getPath();
        String name = handling.getFile().getName();

        handling.setComment(comment);
        handling.setStatus(status);
        handling.setDateOfInspection(LocalDateTime.now());

//        byte[] fileBytes = file.getBytes();
//
//        try {
//            fileService.deleteFileById(token, new FileDto(
//                    path,
//                    name
//            ));
//
//            fileService.uploadFile(token, new FileRequest(
//                    fileBytes,
//                    path,
//                    "upd_" + file.getOriginalFilename()
//            ));
//        } catch (Exception e) {
//            throw new CallingFileServiceException(e.getMessage());
//        }
//
//
//        handling.getFile().setName("upd_"+file.getOriginalFilename());
//        log.info("upload file done");
        handlingRepository.save(handling);

        return new MessageResponse("File is deleted successfully!");
    }

}
