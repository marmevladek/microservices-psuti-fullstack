package ru.psuti.userservice.service;

import ru.psuti.userservice.dto.req.*;
import ru.psuti.userservice.payload.response.MessageResponse;

public interface AdminService {

    MessageResponse updateRequirementTest(Long id, RequirementDto requirementDto);
}
