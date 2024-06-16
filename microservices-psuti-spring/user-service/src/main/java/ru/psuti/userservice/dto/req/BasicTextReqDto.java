package ru.psuti.userservice.dto.req;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BasicTextReqDto {

    private double spacingBetween;

    private double spacingAfter;

    private String alignment;

    private double indentationFirstLine;

    private String fontFamily;

    private int fontSize;
}
