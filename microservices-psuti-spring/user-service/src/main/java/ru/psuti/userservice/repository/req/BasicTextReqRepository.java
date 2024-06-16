package ru.psuti.userservice.repository.req;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.psuti.userservice.model.req.BasicTextReq;

public interface BasicTextReqRepository extends JpaRepository<BasicTextReq, Long> {
}
