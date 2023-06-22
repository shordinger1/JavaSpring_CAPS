package com.team4.caps.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

@Entity
@Data
public class CourseSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "course_lecturer_id")
    private CourseLecturer courseLecturer;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;




    public CourseSchedule(CourseLecturer courseLecturer, Schedule schedule) {
        this.courseLecturer = courseLecturer;
        this.schedule = schedule;
    }




}
