package ru.psuti.fileservice.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.psuti.fileservice.message.ResponseMessage;
import ru.psuti.fileservice.payload.FileDto;

import java.nio.file.Path;

public interface FileService {

    void init(Path root);

    ResponseMessage save(MultipartFile file, String path, String name);

    Resource downloadFile(String path, String name);

    boolean delete(FileDto fileDto);

}
