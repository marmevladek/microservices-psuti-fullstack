package ru.psuti.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.psuti.userservice.model.FileInfo;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HandlingDto {

    private Long id;
    private Long studentUid;
    private Long teacherUid;
    private String comment;
    private Boolean status;
    //    private Instant departureDate;
    //    private Instant dateOfInspection;
    private FileInfo file;

}
