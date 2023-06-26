package com.team4.caps.domain.param;

import lombok.Data;

import java.util.Date;

/**
 * For search course query
 * @Author susie
 */
@Data
public class SearchCourseParam extends PageParam{

    String courseId;

    String courseName;

    String lecturerName;

    Date courseStartDate;

    Date courseEndDate;

}
