package ru.psuti.userservice.mapper;

import ru.psuti.userservice.dto.req.*;
import ru.psuti.userservice.model.req.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RequirementMapper {

    public static Requirement mapToRequirement(RequirementDto requirementDto) {
        return new Requirement(
                requirementDto.getPgMarLeft() * 567,
                requirementDto.getPgMarRight() * 567,
                requirementDto.getPgMarTop() * 567,
                requirementDto.getPgMarBottom() * 567,
                mapToBasicTextReq(requirementDto.getBasicTextReqDto()),
                mapToCodeReq(requirementDto.getCodeReqDto()),
                mapToHeaderReq(requirementDto.getHeaderReqDto()),
                mapToListReq(requirementDto.getListReqDto()),
                mapToPictureReq(requirementDto.getPictureReqDto()),
                mapToTableTextReq(requirementDto.getTableTextReqDto())
        );
    }

    private static BasicTextReq mapToBasicTextReq(BasicTextReqDto basicTextReqDto) {
        return new BasicTextReq(
                toDoubleValue(basicTextReqDto.getSpacingBetween()),
                toDoubleValue(basicTextReqDto.getSpacingAfter()),
                basicTextReqDto.getAlignment(),
                toDoubleValue(basicTextReqDto.getIndentationFirstLine() * 567),
                basicTextReqDto.getFontFamily(),
                basicTextReqDto.getFontSize()
        );
    }

    private static CodeReq mapToCodeReq(CodeReqDto codeReqDto) {
         return new CodeReq(
                toDoubleValue(codeReqDto.getSpacingBetween()),
                toDoubleValue(codeReqDto.getSpacingAfter()),
                codeReqDto.getAlignment(),
                toDoubleValue(codeReqDto.getIndentationFirstLine() * 567),
                codeReqDto.getFontFamily(),
                codeReqDto.getFontSize()
        );
    }

    private static HeaderReq mapToHeaderReq(HeaderReqDto headerReqDto) {
        return new HeaderReq(
                toDoubleValue(headerReqDto.getSpacingBetween()),
                toDoubleValue(headerReqDto.getSpacingAfter()),
                headerReqDto.getAlignment(),
                toDoubleValue(headerReqDto.getIndentationFirstLine() * 567),
                headerReqDto.getFontFamily(),
                headerReqDto.getFontSize()
        );
    }

    private static ListReq mapToListReq(ListReqDto listReqDto) {
         return new ListReq(
                toDoubleValue(listReqDto.getSpacingBetween()),
                toDoubleValue(listReqDto.getSpacingAfter()),
                listReqDto.getAlignment(),
                toDoubleValue(listReqDto.getIndentationFirstLine() * 567),
                listReqDto.getFontFamily(),
                listReqDto.getFontSize()
        );
    }

    private static PictureReq mapToPictureReq(PictureReqDto pictureReqDto) {
         return new PictureReq(
                toDoubleValue(pictureReqDto.getSpacingBetween()),
                toDoubleValue(pictureReqDto.getSpacingAfter()),
                pictureReqDto.getAlignment(),
                toDoubleValue(pictureReqDto.getIndentationFirstLine() * 567),
                pictureReqDto.getFontFamily(),
                pictureReqDto.getFontSize()
        );
    }

    private static TableTextReq mapToTableTextReq(TableTextReqDto tableTextReqDto) {
        return new TableTextReq(
                toDoubleValue(tableTextReqDto.getSpacingBetween()),
                toDoubleValue(tableTextReqDto.getSpacingAfter()),
                tableTextReqDto.getAlignment(),
                toDoubleValue(tableTextReqDto.getIndentationFirstLine() * 567),
                tableTextReqDto.getFontFamily(),
                tableTextReqDto.getFontSize()
        );
    }


    private static double toDoubleValue(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
