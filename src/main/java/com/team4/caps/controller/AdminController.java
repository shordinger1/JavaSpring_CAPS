package com.team4.caps.controller;

import com.team4.caps.config.SecurityConfig;
import com.team4.caps.model.*;
import com.team4.caps.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
    private final AdminService adminService;

    private final StudentService studentService;

    private final LecturerService lecturerService;

    private final CourseLecturerService courseLecturerService;

    private final CourseStudentService courseStudentService;

    @Autowired
    public AdminController(AdminService adminService, StudentService studentService, LecturerService lecturerService, CourseLecturerService courseLecturerService, CourseStudentService courseStudentService) {
        this.adminService = adminService;
        this.studentService = studentService;
        this.lecturerService = lecturerService;
        this.courseLecturerService = courseLecturerService;
        this.courseStudentService = courseStudentService;
    }


    @PostMapping("/login")
    public String login(@RequestBody String body, HttpSession session, Model model) {
        var paraList = body.split("&");
        String password = paraList[2].substring(9);
        String username = paraList[1].substring(9);
        String type = paraList[0].substring(5);
        password = SecurityConfig.encoder(password);
        //System.out.println(password);
        //System.out.println(username);
        //System.out.println(type);
        String finalPassword = password;
        switch (type) {
            case "Admin" -> {
                var admin = adminService.getAllAdmins().stream().filter(admin1 -> username.equals(admin1.getUsername()) &&
                        finalPassword.equals(admin1.getPassword())).toList();
                if (admin.size() == 1) {
                    session.setAttribute("id", admin.get(0).getId());
                    session.setAttribute("username", username);
                    session.setAttribute("type", type);
                } else return "index";
                return "redirect:/students";

            }
            case "Student" -> {
                var students = studentService.getAllStudents().stream().filter(admin1 -> username.equals(admin1.getUsername()) &&
                        finalPassword.equals(admin1.getPassword())).toList();
                if (students.size() == 1) {
                    Student student = students.get(0);
                    int id = student.getId();
                    model.addAttribute("student", student);
                    var courses = new java.util.ArrayList<>(courseLecturerService.getAllCourseLecturers().stream().filter(
                            courseLecturer -> courseLecturer.getCourseCapacity() > courseLecturer.getEnrolled()).toList());
                    var coursesAlreadyEnrolled = courseStudentService.getAllCourseStudents().stream().filter(
                            courseStudent1 -> courseStudent1.getStudent().getId() == id).toList();
                    courses.removeAll(coursesAlreadyEnrolled.stream().map(CourseStudent::getCourseLecturer).toList());
                    //courses.forEach(courseLecturer -> courseLecturer.setCourseSchedule(new CourseSchedule()));
                    //coursesAlreadyEnrolled.forEach(courseLecturer -> courseLecturer.getCourseLecturer().setCourseSchedule(new CourseSchedule()));
                    model.addAttribute("courses",courses);
                    model.addAttribute("coursesAlreadyEnrolled",coursesAlreadyEnrolled);
                    model.addAttribute("course",new CourseLecturer());
                    session.setAttribute("id", id);
                    session.setAttribute("username", username);
                    session.setAttribute("type", type);
                    return "studentIndex";
                } else return "index";
                //return "studentIndex";
            }
            case "Lecturer" -> {
                var lecturer = lecturerService.getAllLecturers().stream().filter(admin1 -> username.equals(admin1.getUsername()) &&
                        finalPassword.equals(admin1.getPassword())).toList();
                if (lecturer.size() == 1) {
                    session.setAttribute("id", lecturer.get(0).getId());
                    session.setAttribute("username", username);
                    session.setAttribute("type", type);
                } else return "index";
                return "redirect:/lecturer/course/"+lecturer.get(0).getId();
            }
            default -> {
                return "index";
            }
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        System.out.println(session.getId());
        System.out.println(session.getAttribute("username"));
        session.invalidate();
        return "index";
    }

}
