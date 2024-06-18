package ru.psuti.userservice.model.req;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "requirements")
@Getter
@Setter
@NoArgsConstructor
public class Requirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pg_mar_left")
    private Integer pgMarLeft;

    @Column(name = "pg_mar_right")
    private Integer pgMarRight;

    @Column(name = "pg_mar_top")
    private Integer pgMarTop;

    @Column(name = "pg_mar_bottom")
    private Integer pgMarBottom;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basic_text")
    private BasicTextReq basicTextReq;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code")
    private CodeReq codeReq;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "header")
    private HeaderReq headerReq;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list")
    private ListReq listReq;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picture")
    private PictureReq pictureReq;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_text")
    private TableTextReq tableTextReq;

    public Requirement(int pgMarLeft, int pgMarRight, int pgMarTop, int pgMarBottom, BasicTextReq basicTextReq, CodeReq codeReq, HeaderReq headerReq, ListReq listReq, PictureReq pictureReq, TableTextReq tableTextReq) {
        this.pgMarLeft = pgMarLeft;
        this.pgMarRight = pgMarRight;
        this.pgMarTop = pgMarTop;
        this.pgMarBottom = pgMarBottom;
        this.basicTextReq = basicTextReq;
        this.codeReq = codeReq;
        this.headerReq = headerReq;
        this.listReq = listReq;
        this.pictureReq = pictureReq;
        this.tableTextReq = tableTextReq;
    }
}
