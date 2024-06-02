package ru.psuti.fileservice.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDocumentChecker {

    private String tempFile;
    private List<String> MessageDocumentChecker;

    public ResponseDocumentChecker(String tempFile, List<String> messageDocumentChecker) {
        this.tempFile = tempFile;
        this.MessageDocumentChecker = messageDocumentChecker;
    }
}
