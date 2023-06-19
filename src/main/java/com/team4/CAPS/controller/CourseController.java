package com.team4.caps.controller;

import com.team4.caps.model.Course;
import com.team4.caps.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/all")
    public String getAllCourses(Model model)
    {
        var courses=courseService.getAllCourses();
        model.addAttribute("courses",courses);
        return "courses";
    }
    
    @GetMapping("/{id}")
    public String GetOneCourse(@PathVariable Integer id,Model model)
    {
        var course=courseService.getCourseById(id);
        model.addAttribute("course_"+id.toString(),course);
        return "course";
    }
    
    @PostMapping("/update/{id}")
    public String updateCourse(@RequestBody Course course,@PathVariable Integer id,Model model)
    {
        var status=courseService.updateCourseById(id,course);
        model.addAttribute("status",status);
        return "courses";
    }
    
    @GetMapping ("delete/{id}")
    public String deleteCourse(@PathVariable Integer id,Model model)
    {
        var status=courseService.deleteCourseById(id);
        model.addAttribute("status",status);
        return "courses";
    }

}

