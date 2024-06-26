package ru.psuti.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.psuti.userservice.model.Handling;

import java.util.List;

public interface HandlingRepository extends JpaRepository<Handling, Long> {
    List<Handling> findAllByStudentUid(Long studentUid);
    List<Handling> findAllByTeacherUid(Long teacherUid);
    List<Handling> findByStatus(Boolean status);
}
