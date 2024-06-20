package ru.psuti.userservice.mapper;

import ru.psuti.userservice.model.Handling;
import ru.psuti.userservice.dto.HandlingDto;
import ru.psuti.userservice.payload.response.HandlingResponse;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class HandlingMapper {

    public static HandlingResponse mapToResponseHandling(Handling handling) {
        return new HandlingResponse(
                handling.getId(),
                handling.getStudentUid(),
                handling.getTeacherUid(),
                handling.getComment(),
                handling.getStatus(),
                toDate(handling.getDepartureDate()),
                toTime(handling.getDepartureDate()),
                toDate(handling.getDateOfInspection()),
                toTime(handling.getDateOfInspection()),
                handling.getFile()
        );
    }

    public static HandlingDto mapToHandlingDto(Handling handling) {
        return new HandlingDto(
                handling.getId(),
                handling.getStudentUid(),
                handling.getTeacherUid(),
                handling.getComment(),
                handling.getStatus(),
                handling.getFile()
        );

    }

    private static String toDate(LocalDateTime dateTime) {
        if (dateTime == null) return null;

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        return dateTime.format(dateTimeFormatter);
    }

    private static String toTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return dateTime.format(dateTimeFormatter);
    }
}
