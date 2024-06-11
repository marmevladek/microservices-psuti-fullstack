package ru.psuti.fileservice.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileRequest {
    private byte[] fileBytes;
    private String path;
    private String name;
}
