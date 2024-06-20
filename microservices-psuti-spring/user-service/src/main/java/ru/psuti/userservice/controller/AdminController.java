package ru.psuti.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.psuti.userservice.dto.req.*;
import ru.psuti.userservice.payload.response.MessageResponse;
import ru.psuti.userservice.service.AdminService;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update-requirement/{id}")
    public ResponseEntity<?> updateRequirementTest(@PathVariable("id") Long id, @RequestBody RequirementDto requirementDto) {
        try {
            return new ResponseEntity<>(adminService.updateRequirementTest(id, requirementDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
