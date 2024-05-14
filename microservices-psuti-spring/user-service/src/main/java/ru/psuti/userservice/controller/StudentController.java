package ru.psuti.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.psuti.userservice.payload.ResponseMessage;
import ru.psuti.userservice.payload.request.RequestHandlingUid;
import ru.psuti.userservice.payload.response.ResponseHandling;
import ru.psuti.userservice.service.StudentService;

import java.io.IOException;
import java.util.List;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/main")
    public ResponseEntity<ResponseMessage> sendHandling(@RequestHeader("Authorization") String token,
                                                        @RequestParam("file") MultipartFile file,
                                                        @RequestParam("uid") String uid,
                                                        @RequestHeader("Content-Type") String contentType) throws IOException {
        return new ResponseEntity<>(studentService.sendHandling(token, file, uid, contentType), HttpStatus.CREATED);
    }

    @GetMapping("/history")
    public ResponseEntity<List<ResponseHandling>> getHandlingHistory(@RequestBody RequestHandlingUid requestHandlingUid) throws IOException {
        return new ResponseEntity<>(studentService.getHandlingHistory(requestHandlingUid.getUid()), HttpStatus.OK);
    }

    @DeleteMapping("/history/delete/{id}")
    public ResponseEntity<ResponseMessage> deleteHandlingById(@RequestHeader("Authorization") String token,
                                                              @PathVariable("id") Long id) throws IOException {
        studentService.deleteHandlingById(token, id);
        return new ResponseEntity<>(new ResponseMessage("Deleted Successfully!"), HttpStatus.OK);
    }
}
