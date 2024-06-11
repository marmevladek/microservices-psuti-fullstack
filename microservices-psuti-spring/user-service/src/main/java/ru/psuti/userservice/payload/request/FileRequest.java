package ru.psuti.userservice.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileRequest {
    private byte[] fileBytes;
    private String path;
    private String name;
}
