package com.team4.caps.controller;

import com.team4.caps.model.CourseLecturer;
import com.team4.caps.model.CourseStudent;
import com.team4.caps.service.CourseLecturerService;
import com.team4.caps.service.CourseStudentService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class CourseStudentController {

    private final CourseStudentService courseStudentService;
    private final CourseLecturerService courseLecturerService;

    @Autowired
    public CourseStudentController(CourseStudentService courseStudentService, CourseLecturerService courseLecturerService) {
        this.courseStudentService = courseStudentService;
        this.courseLecturerService = courseLecturerService;
    }
    @GetMapping("/studentCourse/all")
    public String getAllCourseStudents(Model model)
    {
        var courseStudents=courseStudentService.getAllCourseStudents();
        model.addAttribute("courseStudents",courseStudents);
        return "CourseStudents";
    }

    @GetMapping("/studentCourse/{id}")
    public String GetOneCourseStudent(@PathVariable Integer id,Model model)
    {
        var courseStudent=courseStudentService.getCourseStudentById(id);
        model.addAttribute("courseStudent_"+id.toString(),courseStudent);
        return "courseStudent";
    }
    //viewcourse-enrolment.html
    @GetMapping("/studentCourse/courseLecturer/{id}")
    public String GetAllCourseStudentByCourseLecturerId(@PathVariable Integer id,Model model)
    {
        var courseStudent=courseStudentService.getAllCourseStudents().stream().
                filter(courseStudent1 -> courseStudent1.getCourseLecturer().getId()==id).toList();
        model.addAttribute("courseStudents",courseStudent);
        System.out.println(courseStudent);
        return "viewcourse-enrolment";
    }
    @PostMapping("/studentCourse/update/{id}")
    public String updateCourseStudent(@RequestBody CourseStudent courseStudent, @PathVariable Integer id, Model model)
    {
        var status=courseStudentService.updateCourseStudentById(id,courseStudent);
        model.addAttribute("status",status);
        return "courseStudents";
    }

    @PostMapping("/StudentCourse/add/{id}")
    public String createCourseStudent(@RequestBody CourseStudent courseStudent, @PathVariable Integer id, Model model)
    {
        CourseLecturer courseLecturer=courseLecturerService.getCourseLecturerById(courseStudent.getCourseLecturer().getId());
        boolean status=true;
        if(courseLecturer.getCourseCapacity()>courseLecturer.getEnrolled()){
            status=courseStudentService.createCourseStudentById(id,courseStudent);
        }
        else status =false;
        model.addAttribute("status",status);
        return "courseStudents";
    }

    @GetMapping ("/StudentCourse/delete/{id}")
    public String deleteCourseStudent(@PathVariable Integer id,Model model)
    {
        var status=courseStudentService.deleteCourseStudentById(id);
        model.addAttribute("status",status);
        return "courseStudents";
    }
}
