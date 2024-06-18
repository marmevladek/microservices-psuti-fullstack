package ru.psuti.userservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "handlings")
@Getter
@Setter
@NoArgsConstructor
public class Handling {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "student_uid")
    private Long studentUid;

    @Column(name = "teacher_uid")
    private Long teacherUid;

    @Column(name = "comment")
    private String comment;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "departure_date")
    private LocalDateTime departureDate;

    @Column(name = "date_of_inspection")
    private LocalDateTime dateOfInspection;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "file_id")
    private FileInfo file;

    public Handling(Long studentUid,
                    Long teacherUid,
                    String comment,
                    Boolean status,
                    LocalDateTime departureDate,
                    LocalDateTime dateOfInspection,
                    FileInfo file) {
        this.studentUid = studentUid;
        this.teacherUid = teacherUid;
        this.comment = comment;
        this.status = status;
        this.departureDate = departureDate;
        this.dateOfInspection = dateOfInspection;
        this.file = file;
    }

    //    @Column(name = "student_group")
//    private String studentGroup;
//
//    @Column(name = "student_name")
//    private String studentName;
//
//    @Column(name = "student_lastname")
//    private String studentLastName;
//
//    @Column(name = "departure_date")
//    private Instant departureDate;
//
//    @Column(name = "date_of_inpection")
//    private Instant dateOfInspection;
//
//    @Column(name = "comment")
//    private String comment;
//
//    @Column(name = "status")
//    private Boolean status;
//
//    @Column(name = "student_uid")
//    private String studentUid;
//
//    @Column(name = "teacher_uid")
//    private String teacherUid;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId
//    @JoinColumn(name = "file_id")
//    private FileInfo file;
//
//
//    public Handling(String studentGroup,
//                    String studentName,
//                    String studentLastName,
//                    Instant departureDate,
//                    Instant dateOfInspection,
//                    String comment,
//                    Boolean status,
//                    String studentUid,
//                    String teacherUid,
//                    FileInfo file) {
//        this.studentGroup = studentGroup;
//        this.studentName = studentName;
//        this.studentLastName = studentLastName;
//        this.departureDate = departureDate;
//        this.dateOfInspection = dateOfInspection;
//        this.comment = comment;
//        this.status = status;
//        this.studentUid = studentUid;
//        this.teacherUid = teacherUid;
//        this.file = file;
//    }
}
