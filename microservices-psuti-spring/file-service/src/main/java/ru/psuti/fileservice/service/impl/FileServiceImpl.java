package ru.psuti.fileservice.service.impl;

import lombok.extern.log4j.Log4j2;
import ru.psuti.fileservice.message.ResponseMessage;
import ru.psuti.fileservice.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@Log4j2
public class FileServiceImpl implements FileService {

    private final Path root = Paths.get(
            "/Users/vladislavtezev/projects/microservices-psuti-fullstack/microservices-psuti-react/public");

    @Override
    public void init(Path root) {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize filesystem");
        }
    }

    @Override
    public ResponseMessage save(MultipartFile file, String path, String name) {
        try {
            Path newRoot = Path.of(this.root + path);
            init(newRoot);
            Files.copy(file.getInputStream(), newRoot.resolve(Objects.requireNonNull(name)));
        } catch (IOException e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("File already exists");
            }
        }

        return new ResponseMessage("File saved successfully");
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1)
                    .filter(path ->
                            !path.equals(this.root))
                    .map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not read the files!");
        }
    }
}
