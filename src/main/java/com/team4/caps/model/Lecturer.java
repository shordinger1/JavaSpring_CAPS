package com.team4.caps.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Lecturer extends Person{
    @ManyToOne
    @JoinColumn(name = "lecturer_faculty_id")
    private Faculty faculty;
}
