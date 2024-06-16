package ru.psuti.userservice.repository.req;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.psuti.userservice.model.req.ListReq;

public interface ListReqRepository extends JpaRepository<ListReq, Long> {
}
