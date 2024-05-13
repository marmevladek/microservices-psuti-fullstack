package ru.psuti.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.psuti.userservice.model.FileInfo;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
}
