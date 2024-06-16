package ru.psuti.userservice.model.req;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "picture")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PictureReq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double spacingBetween;


    private double spacingAfter;


    private String alignment;


    private double indentationFirstLine;


    private String fontFamily;


    private int fontSize;

    public PictureReq(double spacingBetween, double spacingAfter, String alignment, double indentationFirstLine, String fontFamily, int fontSize) {
        this.spacingBetween = spacingBetween;
        this.spacingAfter = spacingAfter;
        this.alignment = alignment;
        this.indentationFirstLine = indentationFirstLine;
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
    }
}
