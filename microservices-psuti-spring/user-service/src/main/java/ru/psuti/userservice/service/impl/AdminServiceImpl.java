package ru.psuti.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.psuti.userservice.dto.req.*;
import ru.psuti.userservice.mapper.RequirementMapper;
import ru.psuti.userservice.model.req.*;
import ru.psuti.userservice.payload.response.MessageResponse;
import ru.psuti.userservice.repository.req.*;
import ru.psuti.userservice.service.AdminService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final RequirementRepository requirementRepository;
    private final BasicTextReqRepository basicTextReqRepository;
    private final CodeReqRepository codeReqRepository;
    private final HeaderReqRepository headerReqRepository;
    private final ListReqRepository listReqRepository;
    private final PictureReqRepository pictureReqRepository;
    private final TableTextReqRepository tableTextReqRepository;


    @Override
    public MessageResponse updateRequirementTest(Long id, RequirementDto requirementDto) {

        Optional<Requirement> requirement = requirementRepository.findById(id);

        if (requirement.isEmpty()) {
            BasicTextReq basicTextReq = RequirementMapper.mapToRequirement(requirementDto).getBasicTextReq();
            CodeReq codeReq = RequirementMapper.mapToRequirement(requirementDto).getCodeReq();
            HeaderReq headerReq = RequirementMapper.mapToRequirement(requirementDto).getHeaderReq();
            ListReq listReq = RequirementMapper.mapToRequirement(requirementDto).getListReq();
            PictureReq pictureReq = RequirementMapper.mapToRequirement(requirementDto).getPictureReq();
            TableTextReq tableTextReq = RequirementMapper.mapToRequirement(requirementDto).getTableTextReq();

            Requirement updatedRequirement = RequirementMapper.mapToRequirement(requirementDto);

            requirement = Optional.of(new Requirement(
                    updatedRequirement.getPgMarLeft(),
                    updatedRequirement.getPgMarRight(),
                    updatedRequirement.getPgMarTop(),
                    updatedRequirement.getPgMarBottom(),
                    basicTextReq,
                    codeReq,
                    headerReq,
                    listReq,
                    pictureReq,
                    tableTextReq
            ));


            basicTextReqRepository.save(basicTextReq);
            codeReqRepository.save(codeReq);
            headerReqRepository.save(headerReq);
            listReqRepository.save(listReq);
            pictureReqRepository.save(pictureReq);
            tableTextReqRepository.save(tableTextReq);
            requirementRepository.save(requirement.get());
        } else {
            Requirement updateRequirement = requirement.orElseThrow(
                    () -> new RuntimeException("Error occurred while updating requirement")
            );

            Requirement newRequirement = RequirementMapper.mapToRequirement(requirementDto);

            updateRequirement.setPgMarLeft(newRequirement.getPgMarLeft());
            updateRequirement.setPgMarRight(newRequirement.getPgMarRight());
            updateRequirement.setPgMarTop(newRequirement.getPgMarTop());
            updateRequirement.setPgMarBottom(newRequirement.getPgMarBottom());
            updateRequirement.setBasicTextReq(updateRequirement.getBasicTextReq());

            updateRequirement.getBasicTextReq().setSpacingBetween(newRequirement.getBasicTextReq().getSpacingBetween());
            updateRequirement.getBasicTextReq().setSpacingAfter(newRequirement.getBasicTextReq().getSpacingAfter());
            updateRequirement.getBasicTextReq().setAlignment(newRequirement.getBasicTextReq().getAlignment());
            updateRequirement.getBasicTextReq().setIndentationFirstLine(newRequirement.getBasicTextReq().getIndentationFirstLine());
            updateRequirement.getBasicTextReq().setFontFamily(newRequirement.getBasicTextReq().getFontFamily());
            updateRequirement.getBasicTextReq().setFontSize(newRequirement.getBasicTextReq().getFontSize());

            updateRequirement.getCodeReq().setSpacingBetween(newRequirement.getCodeReq().getSpacingBetween());
            updateRequirement.getCodeReq().setSpacingAfter(newRequirement.getCodeReq().getSpacingAfter());
            updateRequirement.getCodeReq().setAlignment(newRequirement.getCodeReq().getAlignment());
            updateRequirement.getCodeReq().setIndentationFirstLine(newRequirement.getCodeReq().getIndentationFirstLine());
            updateRequirement.getCodeReq().setFontFamily(newRequirement.getCodeReq().getFontFamily());
            updateRequirement.getCodeReq().setFontSize(newRequirement.getCodeReq().getFontSize());

            updateRequirement.getHeaderReq().setSpacingBetween(newRequirement.getHeaderReq().getSpacingBetween());
            updateRequirement.getHeaderReq().setSpacingAfter(newRequirement.getHeaderReq().getSpacingAfter());
            updateRequirement.getHeaderReq().setAlignment(newRequirement.getHeaderReq().getAlignment());
            updateRequirement.getHeaderReq().setIndentationFirstLine(newRequirement.getHeaderReq().getIndentationFirstLine());
            updateRequirement.getHeaderReq().setFontFamily(newRequirement.getHeaderReq().getFontFamily());
            updateRequirement.getHeaderReq().setFontSize(newRequirement.getHeaderReq().getFontSize());

            updateRequirement.getListReq().setSpacingBetween(newRequirement.getListReq().getSpacingBetween());
            updateRequirement.getListReq().setSpacingAfter(newRequirement.getListReq().getSpacingAfter());
            updateRequirement.getListReq().setAlignment(newRequirement.getListReq().getAlignment());
            updateRequirement.getListReq().setIndentationFirstLine(newRequirement.getListReq().getIndentationFirstLine());
            updateRequirement.getListReq().setFontFamily(newRequirement.getListReq().getFontFamily());
            updateRequirement.getListReq().setFontSize(newRequirement.getListReq().getFontSize());

            updateRequirement.getPictureReq().setSpacingBetween(newRequirement.getPictureReq().getSpacingBetween());
            updateRequirement.getPictureReq().setSpacingAfter(newRequirement.getPictureReq().getSpacingAfter());
            updateRequirement.getPictureReq().setAlignment(newRequirement.getPictureReq().getAlignment());
            updateRequirement.getPictureReq().setIndentationFirstLine(newRequirement.getPictureReq().getIndentationFirstLine());
            updateRequirement.getPictureReq().setFontFamily(newRequirement.getPictureReq().getFontFamily());
            updateRequirement.getPictureReq().setFontSize(newRequirement.getPictureReq().getFontSize());

            updateRequirement.getTableTextReq().setSpacingBetween(newRequirement.getTableTextReq().getSpacingBetween());
            updateRequirement.getTableTextReq().setSpacingAfter(newRequirement.getTableTextReq().getSpacingAfter());
            updateRequirement.getTableTextReq().setAlignment(newRequirement.getTableTextReq().getAlignment());
            updateRequirement.getTableTextReq().setIndentationFirstLine(newRequirement.getTableTextReq().getIndentationFirstLine());
            updateRequirement.getTableTextReq().setFontFamily(newRequirement.getTableTextReq().getFontFamily());
            updateRequirement.getTableTextReq().setFontSize(newRequirement.getTableTextReq().getFontSize());

            requirementRepository.save(updateRequirement);
        }

        return new MessageResponse("Требования к оформлению документа успешно обновлены!");
    }
}
