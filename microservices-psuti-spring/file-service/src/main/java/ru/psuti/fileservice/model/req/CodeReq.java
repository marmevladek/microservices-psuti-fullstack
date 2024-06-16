package ru.psuti.fileservice.model.req;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "code")
@Getter
@Setter
@NoArgsConstructor
public class CodeReq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int spacingBetween;


    private int spacingAfter;


    private String alignment;


    private int indentationFirstLine;


    private String fontFamily;


    private int fontSize;

    public CodeReq(int spacingBetween, int spacingAfter, String alignment, int indentationFirstLine, String fontFamily, int fontSize) {
        this.spacingBetween = spacingBetween;
        this.spacingAfter = spacingAfter;
        this.alignment = alignment;
        this.indentationFirstLine = indentationFirstLine;
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
    }
}
