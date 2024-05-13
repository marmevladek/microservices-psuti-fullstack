package ru.psuti.fileservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileInfo {
    private String name;
    private String path;

    public FileInfo(String name, String path) {
        this.name = name;
        this.path = path;
    }
}
