package com.team4.caps.model;
import jakarta.persistence.*;

import lombok.Data;

import java.util.Date;


@Entity
@Data
public class Student extends Person{
    private Long enrollmentDate= 0L;

    @ManyToOne
    @JoinColumn(name = "student_faculty_id")
    private Faculty studentfaculty;

    private Float gpa=0.0F;

    public Student() {

    }
}
