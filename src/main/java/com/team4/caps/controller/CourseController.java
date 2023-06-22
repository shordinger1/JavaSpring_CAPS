package com.team4.caps.controller;

import com.team4.caps.model.Course;
import com.team4.caps.service.CourseLecturerService;
import com.team4.caps.service.CourseScheduleService;
import com.team4.caps.service.CourseService;
import com.team4.caps.service.CourseStudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    private final CourseLecturerService courseLecturerService;
    private final CourseScheduleService courseScheduleService;
    private final CourseStudentService courseStudentService;

    @Autowired
    public CourseController(CourseService courseService, CourseLecturerService courseLecturerService, CourseScheduleService courseScheduleService, CourseStudentService courseStudentService) {
        this.courseService = courseService;
        this.courseLecturerService = courseLecturerService;
        this.courseScheduleService = courseScheduleService;
        this.courseStudentService = courseStudentService;
    }

    @GetMapping("/all")
    public String getAllCourses(Model model)
    {
        var courses=courseService.getAllCourses();
        model.addAttribute("courses",courses);
        //model.addAttribute("sessionId",session.getId());
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
        var courseStudents=courseStudentService.getAllCourseStudents();
        for(var c:courseStudents)
        {
            if(c.getCourseLecturer().getCourse().getId()==id)courseStudentService.deleteCourseStudentById(c.getId());
        }
        var courseSchedules=courseScheduleService.getAllCourseSchedules();
        for(var c:courseSchedules)
        {
            if(c.getCourseLecturer().getCourse().getId()==id)courseScheduleService.deleteCourseScheduleById(c.getId());
        }
        var courseLecturers=courseLecturerService.getAllCourseLecturers();
        for(var c:courseLecturers)
        {
            if(c.getCourse().getId()==id)courseLecturerService.deleteCourseLecturerById(c.getId());
        }
        var status=courseService.deleteCourseById(id);
        model.addAttribute("status",status);
        return "courses";
    }

}

