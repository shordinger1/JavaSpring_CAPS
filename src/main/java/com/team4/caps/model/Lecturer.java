package com.team4.caps.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Lecturer extends Person{
    @ManyToOne
    @JoinColumn(name = "lecturer_faculty_id")
    private Faculty faculty;

    public Lecturer() {
        super();
        int roleType=1;
    }
}
