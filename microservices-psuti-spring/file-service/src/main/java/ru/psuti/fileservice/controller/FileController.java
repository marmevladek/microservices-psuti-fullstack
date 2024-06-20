package ru.psuti.fileservice.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.MimeTypeUtils;
import ru.psuti.fileservice.dto.FileDto;
import ru.psuti.fileservice.exception.FileAlreadyExistsException;
import ru.psuti.fileservice.payload.request.FileRequest;
import ru.psuti.fileservice.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@CrossOrigin("http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @PostMapping(value = "/upload")
    public void uploadFile(@RequestBody FileRequest fileRequest) {

        try {
            MultipartFile file = new MockMultipartFile(fileRequest.getName(), fileRequest.getName(), MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE, fileRequest.getFileBytes());
            fileService.save(file, fileRequest.getPath(), fileRequest.getName());
        } catch (FileAlreadyExistsException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> getFile(@RequestParam String path, @RequestParam String name) {
        Resource file = fileService.load(path, name);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @DeleteMapping("/delete")
    public void deleteFileById(@RequestBody FileDto fileDto) {

        try {
            boolean existed = fileService.delete(fileDto);

            if (existed) {
                System.out.println("File deleted successfully");
            }

            System.out.println("File not found");
        } catch (Exception e) {
            System.out.println("Could not delete the file");
        }
    }

}
