package ru.psuti.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.psuti.userservice.payload.HandlingDto;
import ru.psuti.userservice.payload.ResponseMessage;
import ru.psuti.userservice.payload.request.RequestHandlingUid;
import ru.psuti.userservice.payload.response.ResponseHandling;
import ru.psuti.userservice.service.TeacherService;

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

    @PutMapping("/handling/{id}")
    public ResponseEntity<ResponseMessage> updateHandling(@PathVariable("id") Long id,
                                                          @RequestBody HandlingDto handlingDto) {
        return new ResponseEntity<>(teacherService.updateHandling(id, handlingDto), HttpStatus.OK);
    }


}
