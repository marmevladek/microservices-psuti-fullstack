package ru.psuti.fileservice.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.psuti.fileservice.dto.FileDto;

import java.nio.file.Path;

public interface FileService {

    void init(Path root);

    void save(MultipartFile file, String path, String name);

    Resource load(String path, String name);

    boolean delete(FileDto fileDto);

}
