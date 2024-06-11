package ru.psuti.fileservice.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DocumentCheckerResponse {

    private String tempFile;
    private List<String> errors;

    public DocumentCheckerResponse(String tempFile, List<String> messageDocumentChecker) {
        this.tempFile = tempFile;
        this.errors = messageDocumentChecker;
    }
}
