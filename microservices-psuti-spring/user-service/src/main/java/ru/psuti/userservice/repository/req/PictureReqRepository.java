package ru.psuti.userservice.repository.req;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.psuti.userservice.model.req.PictureReq;

public interface PictureReqRepository extends JpaRepository<PictureReq, Long> {
}
