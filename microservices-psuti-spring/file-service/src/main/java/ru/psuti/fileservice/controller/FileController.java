package ru.psuti.fileservice.controller;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.MimeTypeUtils;
import ru.psuti.fileservice.payload.FileDto;
import ru.psuti.fileservice.payload.RequestUpload;
import ru.psuti.fileservice.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.psuti.fileservice.service.impl.DocumentChecker;


@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/files") // /api
public class FileController {

    private final FileService fileService;

    @PostMapping(value = "/upload")
    public void uploadFile(@RequestBody RequestUpload requestUpload) {
        MultipartFile file = new MockMultipartFile(requestUpload.getName(), requestUpload.getName(), MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE, requestUpload.getFileBytes());
        fileService.save(file, requestUpload.getPath(), requestUpload.getName());
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String path, @RequestParam String name) {
        return ResponseEntity.ok().body(fileService.downloadFile(path, name));
    }

    @DeleteMapping("/delete")
    public void deleteFileById(@RequestBody FileDto fileDto) {

        try {
            boolean existed = fileService.delete(fileDto);

            if (existed) {
//                return new ResponseEntity<>(new ResponseMessage("File deleted successfully"), HttpStatus.OK);
                System.out.println("File deleted successfully");
            }

//            return new ResponseEntity<>(new ResponseMessage("File not found"), HttpStatus.NOT_FOUND);
            System.out.println("File not found");
        } catch (Exception e) {
            System.out.println("Could not delete the file");
//            return new ResponseEntity<>(new ResponseMessage("Could not delete the file: " + fileDto.getName() + ". Error: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
