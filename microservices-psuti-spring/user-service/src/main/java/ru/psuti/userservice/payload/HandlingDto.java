package ru.psuti.userservice.payload;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.psuti.userservice.model.FileInfo;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HandlingDto {

    private Long id;

    private String studentGroup;

    private String studentName;

    private String studentLastName;

//    private Instant departureDate;
//
//    private Instant dateOfInspection;

    private String comment;

    private Boolean status;

    private String studentUid;

    private String teacherUid;

//    private FileInfo file;

}
