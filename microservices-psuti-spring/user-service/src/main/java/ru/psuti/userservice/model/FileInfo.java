package ru.psuti.userservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="files")
@Getter
@Setter
@NoArgsConstructor
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="path")
    private String path;

    @Column(name="name")
    private String name;



    public FileInfo(String path, String name) {
        this.path = path;
        this.name = name;
    }
}
