package ru.psuti.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.psuti.userservice.payload.HandlingDto;
import ru.psuti.userservice.payload.ResponseMessage;
import ru.psuti.userservice.payload.response.ResponseHandling;
import ru.psuti.userservice.service.TeacherService;

import java.io.IOException;
import java.util.List;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping("/main")
    public ResponseEntity<List<ResponseHandling>> getMainTeacher() {
        return new ResponseEntity<>(teacherService.getHandlingList(), HttpStatus.OK);
    }

    @GetMapping("/handling/{id}")
    public ResponseEntity<HandlingDto> getHandlingById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(teacherService.getHandlingById(id), HttpStatus.OK);
    }

    @PutMapping("/handling/{id}")
    public ResponseEntity<ResponseMessage> updateHandling(@RequestHeader("Authorization") String token,
                                                          @PathVariable("id") Long id,
                                                          @RequestParam("file") MultipartFile file,
                                                          @RequestParam("comment") String comment,
                                                          @RequestParam("status") Boolean status) throws IOException {
        return new ResponseEntity<>(teacherService.updateHandling(token, id, file, comment, status), HttpStatus.OK);
    }


}
