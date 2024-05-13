package ru.psuti.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.psuti.userservice.model.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
