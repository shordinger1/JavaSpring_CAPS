package com.team4.caps.controller;

import com.team4.caps.model.Course;
import com.team4.caps.model.CourseLecturer;
import com.team4.caps.service.CourseLecturerService;
import com.team4.caps.service.CourseScheduleService;
import com.team4.caps.service.CourseStudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class CourseLecturerController {

    private final CourseLecturerService courseLecturerService;
    private final CourseScheduleService courseScheduleService;
    private final CourseStudentService courseStudentService;

    @Autowired
    public CourseLecturerController(CourseLecturerService courseLecturerService, CourseScheduleService courseScheduleService, CourseStudentService courseStudentService) {
        this.courseLecturerService = courseLecturerService;
        this.courseScheduleService = courseScheduleService;
        this.courseStudentService = courseStudentService;
    }
    @GetMapping("/all")
    public String getAllCourseLecturers(Model model)
    {
        var courseLecturers=courseLecturerService.getAllCourseLecturers();
        model.addAttribute("courseLecturers",courseLecturers);
        return "CourseLecturers";
    }
/*
    @GetMapping("/available")
    public String getAllAvailableCourseLecturers(Model model)
    {
        var courseLecturers=courseLecturerService.getAllCourseLecturers().stream().map(CourseLecturer::getStatus);
        model.addAttribute("courseLecturers",courseLecturers);

        return "CourseLecturers";
    }
*/

    @GetMapping("/lecturer/course/{id}")
    public String getCoursesTaught(@PathVariable Integer id, Model model)
    {
        //Integer id= (Integer) session.getAttribute("id");
        List<CourseLecturer> coursesLecturer = courseLecturerService.getAllCourseLecturers().stream().filter(courseLecturer1 -> courseLecturer1.getLecturer().getId()==id).toList();
        model.addAttribute("courseLecturers",coursesLecturer);
        //model.addAttribute("courses",coursesLecturer.stream().map(CourseLecturer::getCourse));
        //model.addAttribute("classrooms",coursesLecturer.stream().map(CourseLecturer::getClassroom));
        return "coursesTaught";
    }



    @GetMapping("/set")
    public String SetAvailableCourseLecturersById(@PathVariable Integer id,Model model)
    {
        var courseLecturer=courseLecturerService.getCourseLecturerById(id);
        var status=courseLecturerService.updateCourseLecturerById(id,courseLecturer);
        model.addAttribute("status",status);
        return "courseLecturers";
    }

    @GetMapping("/{id}")
    public String GetOneCourseLecturer(@PathVariable Integer id,Model model)
    {
        var courseLecturer=courseLecturerService.getCourseLecturerById(id);
        model.addAttribute("courseLecturer_"+id.toString(),courseLecturer);
        return "courseLecturer";
    }

    @PostMapping("/update/{id}")
    public String updateCourseLecturer(@RequestBody CourseLecturer courseLecturer, @PathVariable Integer id, Model model)
    {
        var status=courseLecturerService.updateCourseLecturerById(id,courseLecturer);
        model.addAttribute("status",status);
        return "courseLecturers";
    }

    @GetMapping ("/delete/{id}")
    public String deleteCourseLecturer(@PathVariable Integer id,Model model)
    {
        var courseStudents=courseStudentService.getAllCourseStudents();
        for(var c:courseStudents)
        {
            if(c.getCourseLecturer().getCourse().getId()==id)courseStudentService.deleteCourseStudentById(c.getId());
        }
        var courseSchedules=courseScheduleService.getAllCourseSchedules();
        for(var c:courseSchedules)
        {
            if(c.getSchedule().getId()==id)courseScheduleService.deleteCourseScheduleById(c.getId());
        }
        var status=courseLecturerService.deleteCourseLecturerById(id);
        model.addAttribute("status",status);
        return "courseLecturers";
    }
}
