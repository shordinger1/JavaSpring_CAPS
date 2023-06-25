package com.team4.caps.controller;

import com.team4.caps.model.CourseLecturer;
import com.team4.caps.model.CourseStudent;
import com.team4.caps.service.CourseLecturerService;
import com.team4.caps.service.CourseService;
import com.team4.caps.service.CourseStudentService;
import com.team4.caps.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class CourseLecturerController {

    private final CourseLecturerService courseLecturerService;
    private final CourseStudentService courseStudentService;

    private final ScheduleService scheduleService;

    private final CourseService courseService;

    @Autowired
    public CourseLecturerController(CourseLecturerService courseLecturerService, CourseStudentService courseStudentService, ScheduleService scheduleService, CourseService courseService) {
        this.courseLecturerService = courseLecturerService;
        this.courseStudentService = courseStudentService;
        this.scheduleService = scheduleService;
        this.courseService = courseService;
    }

    @GetMapping("/all")
    public String getAllCourseLecturers(Model model) {
        var courseLecturers = courseLecturerService.getAllCourseLecturers();
        model.addAttribute("courseLecturers", courseLecturers);
        return "CourseLecturers";
    }


    @GetMapping("/lecturer/course/{id}")
    public String getCoursesTaught(@PathVariable Integer id, Model model) {
        //Integer id= (Integer) session.getAttribute("id");
        List<CourseLecturer> coursesLecturer = courseLecturerService.getAllCourseLecturers().stream().filter(courseLecturer1 -> courseLecturer1.getLecturer().getId() == id).toList();
        model.addAttribute("courseLecturers", coursesLecturer);
        return "coursesTaught";
    }

    @GetMapping("/courseGrade/{id}")
    public String getGradeCourse(@PathVariable Integer id, Model model) {
        //Integer id= (Integer) session.getAttribute("id");
        List<CourseLecturer> coursesLecturer = courseLecturerService.getAllCourseLecturers().stream().filter(courseLecturer1 -> courseLecturer1.getLecturer().getId() == id).toList();
        model.addAttribute("courseLecturers", coursesLecturer);
        return "grade-course-courses";
    }


    @PostMapping("/addCourseLecturer")
    public String addCourseLecturersById( @ModelAttribute CourseLecturer courseLecturer, Model model) {
        //var courseLecturer=courseLecturerService.getCourseLecturerById(id);
        var course = courseService.getAllCourses().stream()
                .filter(course1 -> course1.getCourseName().equals(courseLecturer.getCourse().getCourseName()))
                .toList();
        if (course.size() != 0) {
            courseLecturer.setCourse(course.get(0));
        } else {
            courseService.createCourse(courseLecturer.getCourse());
        }
        var status = courseLecturerService.createCourseLecturer(courseLecturer);
        //courseLecturer=courseLecturerService.getCourseLecturerById(courseLecturer.getId());
        model.addAttribute("status", status);
        return "courses";
    }

    @PostMapping("/updateCourseLecturer/{id}")
    public String updateCourseLecturersById(@PathVariable Integer id, @ModelAttribute CourseLecturer courseLecturer, Model model) {
        var course = courseService.getAllCourses().stream()
                .filter(course1 -> course1.getCourseName().equals(courseLecturer.getCourse().getCourseName()))
                .toList();
        if (course.size() != 0) {
            courseLecturer.setCourse(course.get(0));
        } else {
            courseService.createCourse(courseLecturer.getCourse());
        }
        var status = courseLecturerService.updateCourseLecturerById(courseLecturer.getId(),courseLecturer);
        //courseLecturer=courseLecturerService.getCourseLecturerById(courseLecturer.getId());
        model.addAttribute("status", status);
        return "redirect:/course/update" + id;
    }

    @GetMapping("/{id}")
    public String GetOneCourseLecturer(@PathVariable Integer id, Model model) {
        var courseLecturer = courseLecturerService.getCourseLecturerById(id);
        model.addAttribute("courseLecturer_" + id.toString(), courseLecturer);
        return "courseLecturer";
    }

    @PostMapping("/update/{id}")
    public String updateCourseLecturer(@RequestBody CourseLecturer courseLecturer, @PathVariable Integer id, Model model) {
        var status = courseLecturerService.updateCourseLecturerById(id, courseLecturer);
        model.addAttribute("status", status);
        return "courseLecturers";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourseLecturer(@PathVariable Integer id, Model model) {
        var courseStudents = courseStudentService.getAllCourseStudents();
        for (var c : courseStudents) {
            if (c.getCourseLecturer().getCourse().getId() == id)
                courseStudentService.deleteCourseStudentById(c.getId());
        }
        scheduleService.deleteScheduleById(id);
        var status = courseLecturerService.deleteCourseLecturerById(id);
        model.addAttribute("status", status);
        return "courseLecturers";
    }


    @GetMapping("/manageEnrollment")
    public String enrollment(HttpSession session, ModelMap model)
    {
        Integer id= (Integer) session.getAttribute("id");
        var courseAlreadyEnrolled=courseStudentService.getAllCourseStudents().stream().filter(courseStudent -> courseStudent.getStudent().getId()==id).map(CourseStudent::getCourseLecturer).toList();
        var courseLecturer= new java.util.ArrayList<>(courseLecturerService.getAllCourseLecturers().stream().filter(courseLecturer1 -> courseLecturer1.getEnrolled() < courseLecturer1.getCourseCapacity()).toList());
        courseLecturer.removeAll(courseAlreadyEnrolled);
        model.addAttribute("search_courseLecturer",new CourseLecturer());
        int totalPage= (int) (courseLecturer.size()/5);
        model.addAttribute("pageData",courseLecturer);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("courseLecturers",courseLecturer);
        return "student-enrol-course";
    }


    @PostMapping("/searchEnrollment")
    public String enrollment(@ModelAttribute CourseLecturer search_courseLecturer,ModelMap model) throws IllegalAccessException {
        var enrollmentCourseLecturer=courseLecturerService.getAllCourseLecturers();
        for(var param:CourseLecturer.class.getDeclaredFields())
        {
            param.setAccessible(true);
            if(param.get(search_courseLecturer)!=null)
            {
                enrollmentCourseLecturer=enrollmentCourseLecturer.stream().filter(courseLecturer1 -> {
                    try {
                        return param.get(courseLecturer1)==param.get(search_courseLecturer);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
            }
        }

        int totalPage= (int) (enrollmentCourseLecturer.size()/5);
        model.addAttribute("pageData",enrollmentCourseLecturer);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("search_courseLecturer",search_courseLecturer);
        model.addAttribute("courseLecturers",enrollmentCourseLecturer);
        return "student-enrol-course";
    }
}
