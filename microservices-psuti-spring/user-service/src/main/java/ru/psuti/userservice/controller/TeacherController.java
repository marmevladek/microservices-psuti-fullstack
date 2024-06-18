package ru.psuti.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.psuti.userservice.exception.CallingFileServiceException;
import ru.psuti.userservice.exception.HandlingNotFoundException;
import ru.psuti.userservice.exception.UserServiceCustomException;
import ru.psuti.userservice.payload.request.UpdateHandlingRequest;
import ru.psuti.userservice.payload.response.MessageResponse;
import ru.psuti.userservice.service.TeacherService;


@CrossOrigin("http://localhost:3000/")
@RestController
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class TeacherController {

    private static final String DEFAULT_ERROR_MESSAGE_RESPONSE = "Произошла техническая ошибка. Попробуйте снова.";

    private final TeacherService teacherService;

    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    @GetMapping("/main/{uid}")
    public ResponseEntity<?> getMainTeacher(@PathVariable("uid") Long uid) {
        try {
            return new ResponseEntity<>(teacherService.getHandlingList(uid), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(DEFAULT_ERROR_MESSAGE_RESPONSE), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    @GetMapping("/handling/{id}")
    public ResponseEntity<?> getHandlingById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(teacherService.getHandlingById(id), HttpStatus.OK);
        } catch (HandlingNotFoundException e) {
            return new ResponseEntity<>(new MessageResponse("Обращение не найдено."), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(DEFAULT_ERROR_MESSAGE_RESPONSE), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    @PutMapping("/handling/{id}")
    public ResponseEntity<?> updateHandling(@RequestHeader("Authorization") String token,
                                            @PathVariable("id") Long id, @RequestBody UpdateHandlingRequest updateHandlingRequest
//                                                          @RequestParam("file") MultipartFile file,
                                                          /*@RequestParam("comment") String comment,
                                                          @RequestParam("status") Boolean status*/) {
        try {
            return new ResponseEntity<>(teacherService.updateHandling(token, id, /*file,*/ updateHandlingRequest.getComment(), updateHandlingRequest.getStatus()), HttpStatus.OK);
        } catch (UserServiceCustomException e) {
            return new ResponseEntity<>(new MessageResponse("Файловый сервис временно недоступен, попробуйте позже"), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (CallingFileServiceException e) {
            return new ResponseEntity<>(new MessageResponse("Произошла ошибка при сохранении файла, попробуйте позже."), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(DEFAULT_ERROR_MESSAGE_RESPONSE), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
