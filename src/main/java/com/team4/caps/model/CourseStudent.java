package com.team4.caps.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CourseStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "course_lecturer_id")
    private CourseLecturer courseLecturer;

    @ManyToOne
    @JoinColumn(name ="student_id")
    private Student student;

    private Double grade;

    //0-pending,1-accepted,2-completed,3-rejected,4-banned
    private Integer requestStatus;

    public String status()
    {
        String[] status={
                "pending","accepted","completed","rejected","banned"
        };
        return status[requestStatus];
    }
}
