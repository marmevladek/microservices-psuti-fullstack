package ru.psuti.fileservice.controller;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.MimeTypeUtils;
import ru.psuti.fileservice.model.FileInfo;
import ru.psuti.fileservice.payload.RequestUpload;
import ru.psuti.fileservice.security.JwtUtils;
import ru.psuti.fileservice.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;


@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/files") // /api
public class FileController {

    private final FileService fileService;
    private final JwtUtils jwtUtils;

    @PostMapping(value = "/upload")
    public void uploadFile(@RequestHeader("Authorization") String token,
                                      @RequestBody RequestUpload requestUpload) {
        MultipartFile file = new MockMultipartFile(requestUpload.getName(), requestUpload.getName(), MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE, requestUpload.getFileBytes());

        fileService.save(file, requestUpload.getPath(), requestUpload.getName());
    }



    @GetMapping("/") // /files
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = fileService.loadAll()
                .map(path -> {
                    String filename = path.getFileName().toString();
                    String url = MvcUriComponentsBuilder
                            .fromMethodName(FileController.class, "getFile", path.getFileName().toString())
                            .build()
                            .toString();

                    return new FileInfo(filename, url);
                }).toList();

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/{filename:.+}") // /files/{filename:.+}
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = fileService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
