package ru.psuti.userservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.psuti.userservice.model.FileInfo;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HandlingListResponse {
    private Long id;
    private Long studentUid;
    private Long teacherUid;
    private String comment;
    private Boolean status;
    private String departureDate;
    private String departureTime;
    private String inspectionDate;
    private String inspectionTime;
    private FileInfo file;
    private String studentName;
    private String studentGroup;
}
