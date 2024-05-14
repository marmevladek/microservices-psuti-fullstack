package ru.psuti.fileservice.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.psuti.fileservice.message.ResponseMessage;
import ru.psuti.fileservice.payload.RequestFileDelete;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileService {

    void init(Path root);

    ResponseMessage save(MultipartFile file, String path, String name);

    Resource load(String filename);

    Stream<Path> loadAll();

    boolean delete(RequestFileDelete requestFileDelete);

}
