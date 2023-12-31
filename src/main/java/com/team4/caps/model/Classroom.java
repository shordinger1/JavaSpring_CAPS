package com.team4.caps.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String classRoomName="_";

    private Integer classRoomSize=-1;

    private Integer classRoomVacancy;
}
