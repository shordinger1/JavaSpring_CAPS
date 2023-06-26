package com.team4.caps.domain.dto;

import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

@Data
public class CourseDTO {

    public CourseDTO() {
    }

    public CourseDTO(Integer id, String courseName, Date courseStartDate, Date courseEndDate, Integer courseCredits, Integer courseCapacity, Integer courseVacancy, String faculty) {
        this.id = id;
//        this.courseId = courseId;
        this.courseName = courseName;
//        this.lecturerName = lecturerName;
        this.courseStartDate = DateFormatUtils.format(courseStartDate, DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.getPattern());
        this.courseEndDate = DateFormatUtils.format(courseEndDate, DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.getPattern());
        this.courseCredits = courseCredits;
        this.courseCapacity = courseCapacity;
        this.courseVacancy = courseVacancy;
        this.faculty = faculty;
    }

    private Integer id;

    private String courseId;

    private String courseName;

    private String lecturerName;

    private String courseStartDate;

    private String courseEndDate;
    private Integer courseCredits;
    private Integer courseCapacity;
    private Integer courseVacancy;
    private String faculty;
}
