package com.team4.caps.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;


@Entity
@Data
public class Student extends Person{
    private Long enrollmentDate= 0L;

    @ManyToOne
    @JoinColumn(name = "student_faculty_id")
    private Faculty studentfaculty;

    private Float gpa=0.0F;


}
