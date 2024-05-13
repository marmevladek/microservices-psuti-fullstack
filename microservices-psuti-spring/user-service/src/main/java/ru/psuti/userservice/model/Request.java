package ru.psuti.userservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "requests")
@Getter
@Setter
@NoArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "student_group")
    private String studentGroup;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "student_lastname")
    private String studentLastName;

    @Column(name = "departure_date")
    private Instant departureDate;

    @Column(name = "date_of_inpection")
    private Instant dateOfInspection;

    @Column(name = "comment")
    private String comment;

    @Column(name = "status")
    private Boolean status;


    public Request(String studentGroup, String studentName, String studentLastName, Instant departureDate, Instant dateOfInspection, String comment, Boolean status) {
        this.studentGroup = studentGroup;
        this.studentName = studentName;
        this.studentLastName = studentLastName;
        this.departureDate = departureDate;
        this.dateOfInspection = dateOfInspection;
        this.comment = comment;
        this.status = status;
    }
}
