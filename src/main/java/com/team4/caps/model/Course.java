package com.team4.caps.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Course{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String courseName;

    private Integer courseCredits;

    private Integer courseVacancy;

    private Boolean courseEnrollmentStatus;
    @ManyToOne
    @JoinColumn(name = "course_faculty_id")
    private Faculty faculty;
}
