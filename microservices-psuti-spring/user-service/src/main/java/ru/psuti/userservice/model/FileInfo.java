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


    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "request_id")
    private Request request;

    public FileInfo(String path, String name, Request request) {
        this.path = path;
        this.name = name;
        this.request = request;
    }
}
