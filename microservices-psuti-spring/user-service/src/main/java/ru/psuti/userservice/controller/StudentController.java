package ru.psuti.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.psuti.userservice.exception.*;
import ru.psuti.userservice.payload.request.RequestHandlingUid;
import ru.psuti.userservice.payload.response.MessageResponse;
import ru.psuti.userservice.service.StudentService;


@CrossOrigin("http://localhost:3000/")
@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private static final String DEFAULT_ERROR_MESSAGE_RESPONSE = "Произошла техническая ошибка. Попробуйте снова.";

    private final StudentService studentService;


    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @PostMapping("/main")
    public ResponseEntity<?> sendHandling(@RequestHeader("Authorization") String token,
                                          @RequestParam("file") MultipartFile file,
                                          @RequestParam("uid") String uid,
                                          @RequestHeader("Content-Type") String contentType) {
        try {
            return new ResponseEntity<>(studentService.sendHandling(token, file, uid, contentType), HttpStatus.CREATED);
        } catch (FileWrongFormatException e) {
            return new ResponseEntity<>(new MessageResponse("Файл должен иметь формат .docx"), HttpStatus.BAD_REQUEST);
        } catch (CallingFileServiceException e) {
            return new ResponseEntity<>(new MessageResponse("Произошла ошибка при сохранении файла, попробуйте позже"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (UserServiceCustomException e) {
            return new ResponseEntity<>(new MessageResponse("Файловый сервис временно недоступен, попробуйте позже"), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(DEFAULT_ERROR_MESSAGE_RESPONSE), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping("/history/{uid}")
    public ResponseEntity<?> getHandlingHistory(@PathVariable("uid") Long uid) {
        try {
            return new ResponseEntity<>(studentService.getHandlingHistory(uid), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(new MessageResponse("Не удалось загрузить историю обращений."), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new MessageResponse(DEFAULT_ERROR_MESSAGE_RESPONSE), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
