package com.team4.caps.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Schedule{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer scheduleDayOfWeek;
    private Integer scheduleStartTime;
    private Integer scheduleEndTime;

    public Schedule(int id, Integer scheduleDayOfWeek, Integer scheduleStartTime, Integer scheduleEndTime) {
        this.id = id;
        this.scheduleDayOfWeek = scheduleDayOfWeek;
        this.scheduleStartTime = scheduleStartTime;
        this.scheduleEndTime = scheduleEndTime;
    }

    public Schedule() {
        id=1;
        //scheduleEndTime=-1;
        //scheduleDayOfWeek=-1;
        //scheduleStartTime=-1;

    }

    public boolean isAvailable()
    {
        return id==0;
    }

}
