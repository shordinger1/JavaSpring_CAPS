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
}
