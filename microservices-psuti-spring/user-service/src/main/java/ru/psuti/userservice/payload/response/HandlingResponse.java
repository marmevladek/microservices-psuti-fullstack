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
public class HandlingResponse {
    private Long id;
    private String studentGroup;

    private String studentName;

    private String studentLastName;

//    private Instant departureDate;

//    private Instant dateOfInspection;

    private String comment;

    private Boolean status;
    private FileInfo file;
}