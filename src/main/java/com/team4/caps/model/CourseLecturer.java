package com.team4.caps.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CourseLecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name ="lecturer_id")
    private Lecturer lecturer;

    private Integer enrolled;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public Integer getCourseCapacity()
    {
        return classroom.getClassRoomSize();
    }


}
