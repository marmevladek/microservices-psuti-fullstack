package ru.psuti.userservice.repository.req;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.psuti.userservice.model.req.HeaderReq;

public interface HeaderReqRepository extends JpaRepository<HeaderReq, Long> {
}
