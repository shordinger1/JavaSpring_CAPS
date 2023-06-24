package com.team4.caps.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

@Entity
@Data
public class CourseSchedule {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;



    public CourseSchedule(Integer id,Schedule schedule) {
        this.id=id;
        this.schedule = schedule;
    }


    public CourseSchedule() {
        schedule=new Schedule();
    }
}
