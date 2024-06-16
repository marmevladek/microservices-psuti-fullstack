package ru.psuti.userservice.repository.req;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.psuti.userservice.model.req.CodeReq;

public interface CodeReqRepository extends JpaRepository<CodeReq, Long> {
}
