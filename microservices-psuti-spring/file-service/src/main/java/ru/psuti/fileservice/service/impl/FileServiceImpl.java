package ru.psuti.fileservice.service.impl;

import lombok.extern.log4j.Log4j2;
import ru.psuti.fileservice.message.ResponseMessage;
import ru.psuti.fileservice.payload.FileDto;
import ru.psuti.fileservice.payload.ResponseDocumentChecker;
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
            Path tempDir = Files.createTempDirectory("temp_dir");
            Path tempFile = tempDir.resolve(file.getOriginalFilename());
            Files.write(tempFile, file.getBytes());


            ResponseDocumentChecker checkerResult = DocumentChecker.checkAndCorrectDocument(tempFile.toString());

            Path newRoot = Path.of(this.root + path);
            init(newRoot);

            Files.copy(Paths.get(checkerResult.getTempFile()), newRoot.resolve(Objects.requireNonNull(name)));
        } catch (IOException e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("File already exists");
            }
        }

        return new ResponseMessage("File saved successfully");
    }

    @Override
    public Resource downloadFile(String path, String name) {
        try {
            Path file = Paths.get(this.root + path);

            return new UrlResource(file.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error occurred: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(FileDto fileDto) {
        Path newRoot = Path.of(this.root + fileDto.getPath());
        try {
            Path file = newRoot.resolve(fileDto.getName());
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error occurred: " + e.getMessage());
        }
    }
}
