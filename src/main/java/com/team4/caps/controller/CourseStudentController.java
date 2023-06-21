package com.team4.caps.controller;

import com.team4.caps.model.CourseStudent;
import com.team4.caps.service.CourseStudentService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/course-students")
public class CourseStudentController {

    private final CourseStudentService courseStudentService;

    @Autowired
    public CourseStudentController(CourseStudentService courseStudentService) {
        this.courseStudentService = courseStudentService;
    }
    @GetMapping("/all")
    public String getAllCourseStudents(Model model)
    {
        var courseStudents=courseStudentService.getAllCourseStudents();
        model.addAttribute("courseStudents",courseStudents);
        return "CourseStudents";
    }

    @GetMapping("/{id}")
    public String GetOneCourseStudent(@PathVariable Integer id,Model model)
    {
        var courseStudent=courseStudentService.getCourseStudentById(id);
        model.addAttribute("courseStudent_"+id.toString(),courseStudent);
        return "courseStudent";
    }

    @PostMapping("/update/{id}")
    public String updateCourseStudent(@RequestBody CourseStudent courseStudent, @PathVariable Integer id, Model model)
    {
        var status=courseStudentService.updateCourseStudentById(id,courseStudent);
        model.addAttribute("status",status);
        return "courseStudents";
    }

    @GetMapping ("delete/{id}")
    public String deleteCourseStudent(@PathVariable Integer id,Model model)
    {
        var status=courseStudentService.deleteCourseStudentById(id);
        model.addAttribute("status",status);
        return "courseStudents";
    }
}
