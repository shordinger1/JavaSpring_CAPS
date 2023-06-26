package com.team4.caps.controller;


import com.team4.caps.domain.dto.CourseDTO;
import com.team4.caps.domain.dto.PageDataDTO;
import com.team4.caps.domain.param.SearchCourseParam;
import com.team4.caps.service.CourseSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/enrollment")
public class CourseSelectionController {
    @Autowired
    CourseSelectionService courseSelectionService;


    @PostMapping("/search")
    @ResponseBody
    public PageDataDTO<List<CourseDTO>> searchCourse(@RequestBody SearchCourseParam searchCourseParam) {
        PageDataDTO<List<CourseDTO>> pageDataDTO = courseSelectionService.findCourseByMultipleCondition(searchCourseParam);
        return pageDataDTO;
    }

    @GetMapping("/enroll")
    public String showCourseListForEnrollment() {
        return "course-search-results";
    }

}
