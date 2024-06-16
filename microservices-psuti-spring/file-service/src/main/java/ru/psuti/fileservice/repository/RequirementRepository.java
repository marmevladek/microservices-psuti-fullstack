package ru.psuti.fileservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.psuti.fileservice.model.req.Requirement;

public interface RequirementRepository extends JpaRepository<Requirement, Long> {
}
