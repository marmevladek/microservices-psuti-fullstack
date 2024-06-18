package ru.psuti.userservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HandlingByIdResponse {

    private HandlingResponse handling;
    private String studentGroup;
    private String studentName;
}
