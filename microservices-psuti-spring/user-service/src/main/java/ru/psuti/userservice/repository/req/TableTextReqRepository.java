package ru.psuti.userservice.repository.req;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.psuti.userservice.model.req.TableTextReq;

public interface TableTextReqRepository extends JpaRepository<TableTextReq, Long> {
}
