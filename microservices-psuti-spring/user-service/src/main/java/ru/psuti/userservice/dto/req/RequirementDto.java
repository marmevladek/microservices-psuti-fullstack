package ru.psuti.userservice.dto.req;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequirementDto {

    private int pgMarLeft;

    private int pgMarRight;

    private int pgMarTop;

    private int pgMarBottom;

    private BasicTextReqDto basicTextReqDto;

    private CodeReqDto codeReqDto;

    private HeaderReqDto headerReqDto;

    private ListReqDto listReqDto;

    private PictureReqDto pictureReqDto;

    private TableTextReqDto tableTextReqDto;
}
