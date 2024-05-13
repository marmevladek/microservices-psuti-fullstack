package ru.psuti.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.psuti.userservice.payload.ResponseMessage;
import ru.psuti.userservice.service.StudentService;

import java.io.IOException;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/main")
    public ResponseEntity<ResponseMessage> sendRequest(@RequestParam("file") MultipartFile file,
                                                       @RequestParam("uid") String uid,
                                                       @RequestHeader("Authorization") String token,
                                                       @RequestHeader("Content-Type") String contentType) throws IOException {
        return new ResponseEntity<>(studentService.sendRequest(file, uid, token, contentType), HttpStatus.CREATED);
    }

//    @GetMapping("/request-history")
//    public ResponseEntity<ResponseMyReq> getRequestHistory() {
//    }
}
