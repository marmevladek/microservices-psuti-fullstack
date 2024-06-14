package ru.psuti.userservice.mapper;

import ru.psuti.userservice.model.Handling;
import ru.psuti.userservice.dto.HandlingDto;
import ru.psuti.userservice.payload.response.HandlingResponse;

public class HandlingMapper {

    public static HandlingResponse mapToResponseHandling(Handling handling) {
        return new HandlingResponse(
                handling.getId(),
                handling.getStudentUid(),
                handling.getTeacherUid(),
                handling.getComment(),
                handling.getStatus(),
                handling.getFile()
        );
    }

    public static HandlingDto mapToHandlingDto(Handling handling) {
        return new HandlingDto(
                handling.getId(),
                handling.getStudentUid(),
                handling.getTeacherUid(),
//                handling.getDepartureDate(),
//                handling.getDateOfInspection(),
                handling.getComment(),
                handling.getStatus(),
                handling.getFile()
        );

    }

//    public static Handling mapToHandling(HandlingDto handlingDto) {
//        return new Handling(
//                handlingDto.getStudentGroup(),
//                handlingDto.getStudentName(),
//                handlingDto.getStudentLastName(),
//                handlingDto.getDepartureDate(),
//                handlingDto.getDateOfInspection(),
//                handlingDto.getComment(),
//                handlingDto.getStatus(),
//                handlingDto.getStudentUid(),
//                handlingDto.getTeacherUid(),
//                handlingDto.getFile()
//        );
//    }
}
