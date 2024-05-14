package ru.psuti.userservice.mapper;

import ru.psuti.userservice.model.Handling;
import ru.psuti.userservice.payload.HandlingDto;
import ru.psuti.userservice.payload.response.ResponseHandling;

public class HandlingMapper {

    public static ResponseHandling mapToResponseHandling(Handling handling) {
        return new ResponseHandling(
                handling.getId(),
                handling.getStudentGroup(),
                handling.getStudentName(),
                handling.getStudentLastName(),
                handling.getComment(),
                handling.getStatus(),
                handling.getFile()
        );
    }

    public static HandlingDto mapToHandlingDto(Handling handling) {
        return new HandlingDto(
                handling.getId(),
                handling.getStudentGroup(),
                handling.getStudentName(),
                handling.getStudentLastName(),
//                handling.getDepartureDate(),
//                handling.getDateOfInspection(),
                handling.getComment(),
                handling.getStatus(),
                handling.getStudentUid(),
                handling.getTeacherUid()
//                handling.getFile()
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
