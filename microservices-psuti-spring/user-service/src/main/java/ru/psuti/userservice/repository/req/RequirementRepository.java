package ru.psuti.userservice.repository.req;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.psuti.userservice.model.req.Requirement;

public interface RequirementRepository extends JpaRepository<Requirement, Long> {
}
