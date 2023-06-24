package com.team4.caps.controller;

import com.team4.caps.model.Course;
import com.team4.caps.model.CourseLecturer;
import com.team4.caps.model.Faculty;
import com.team4.caps.model.Lecturer;
import com.team4.caps.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class CourseController {

    private final CourseService courseService;
    private final CourseLecturerService courseLecturerService;
    private final CourseStudentService courseStudentService;

    private final FacultyService facultyService;

    private final LecturerService lecturerService;

    @Autowired
    public CourseController(CourseService courseService, CourseLecturerService courseLecturerService, CourseStudentService courseStudentService, FacultyService facultyService, LecturerService lecturerService) {
        this.courseService = courseService;
        this.courseLecturerService = courseLecturerService;
        this.courseStudentService = courseStudentService;
        this.facultyService = facultyService;
        this.lecturerService = lecturerService;
    }

    @GetMapping("/courses")
    public String getAllCourses(Model model) {
        var courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        //model.addAttribute("sessionId",session.getId());
        return "courses";
    }


    @GetMapping("/course/add")
    public String redirectAddCourse(Model model)
    {
        model.addAttribute("course",new Course());
        List<Faculty> faculties=facultyService.getAllFacultys();
        List<Lecturer> lecturers=lecturerService.getAllLecturers();
        //System.out.println(faculties);
        model.addAttribute("faculties",faculties);
        model.addAttribute("lecturers",lecturers);
        return "course-create";
    }

    //course admin update
    @GetMapping("/course/update/{id}")
    public String redirectUpdateCourse(@PathVariable Integer id,Model model)
    {
        var courseLecturers=courseLecturerService.getAllCourseLecturers().stream()
                .filter(courseLecturer -> courseLecturer.getCourse().getId()==id).toList();
        List<Faculty> faculties=facultyService.getAllFacultys();
        List<Lecturer> lecturers=lecturerService.getAllLecturers();
        model.addAttribute("courseLecturer",new CourseLecturer());
        //List<Course> courses=new ArrayList<>();
        //courses.add(course);
        model.addAttribute("courseLecturers",courseLecturers);
        model.addAttribute("faculties",faculties);
        model.addAttribute("lecturers",lecturers);
        //model.addAttribute("header","Content-Type:application/json;charset=UTF-8");
        return "course-edit";
    }
    @GetMapping("/course/{id}")
    public String GetOneCourse(@PathVariable Integer id, Model model) {
        var course = courseService.getCourseById(id);
        model.addAttribute("course_" + id.toString(), course);
        return "course";
    }

    @PostMapping("/updateCourse/{id}")
    public String updateCourse(@ModelAttribute Course course, @PathVariable Integer id, Model model) {
        var status = courseService.updateCourseById(id, course);
        model.addAttribute("status", status);
        return "courses";
    }

    @GetMapping("/deleteCourse/{id}")
    public String deleteCourse(@PathVariable Integer id, Model model) {
        var courseStudents = courseStudentService.getAllCourseStudents();
        for (var c : courseStudents) {
            if (c.getCourseLecturer().getCourse().getId() == id)
                courseStudentService.deleteCourseStudentById(c.getId());
        }
//        var courseSchedules = courseScheduleService.getAllCourseSchedules();
//        for (var c : courseSchedules) {
//            if (c.getId() == id)
//                courseScheduleService.deleteCourseScheduleById(c.getId());
//        }
        var courseLecturers = courseLecturerService.getAllCourseLecturers();
        for (var c : courseLecturers) {
            if (c.getCourse().getId() == id) courseLecturerService.deleteCourseLecturerById(c.getId());
        }
        var status = courseService.deleteCourseById(id);
        model.addAttribute("status", status);
        return "courses";
    }

}

