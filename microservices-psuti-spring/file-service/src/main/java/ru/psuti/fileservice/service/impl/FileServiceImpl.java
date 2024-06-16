package ru.psuti.fileservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.psuti.fileservice.dto.FileDto;
import ru.psuti.fileservice.exception.FileAlreadyExistsException;
import ru.psuti.fileservice.model.req.Requirement;
import ru.psuti.fileservice.repository.RequirementRepository;
import ru.psuti.fileservice.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
@Log4j2
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final RequirementRepository requirementRepository;

    private final Path root = Paths.get(
            "/Users/vladislavtezev/projects/microservices-psuti-fullstack/microservices-psuti-react/public");

    @Override
    public void init(Path root) {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось инитиацизировать файловую систему.");
        }
    }


    @Override
    public void save(MultipartFile file, String path, String name) {
        try {
            Path tempDir = Files.createTempDirectory("temp_dir");
            Path tempFile = tempDir.resolve(file.getOriginalFilename());
            Files.write(tempFile, file.getBytes());

            Requirement requirement = requirementRepository.findAll().get(0);

            DocumentChecker.checkAndCorrectDocument(tempFile.toString(), requirement);

            Path newRoot = Path.of(this.root + path);
            init(newRoot);

            Files.copy(Paths.get(tempFile.toString()), newRoot.resolve(Objects.requireNonNull(name)));
        } catch (IOException e) {
            throw new FileAlreadyExistsException(e.getMessage());
        }

        log.info("Файл успешно сохранен.");
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
