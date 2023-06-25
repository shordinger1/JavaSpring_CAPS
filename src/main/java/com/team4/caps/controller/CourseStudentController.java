package com.team4.caps.controller;

import com.team4.caps.model.CourseLecturer;
import com.team4.caps.model.CourseStudent;
import com.team4.caps.service.CourseLecturerService;
import com.team4.caps.service.CourseStudentService;
import com.team4.caps.service.StudentService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class CourseStudentController {

    private final CourseStudentService courseStudentService;
    private final CourseLecturerService courseLecturerService;

    private final StudentService studentService;

    @Autowired
    public CourseStudentController(CourseStudentService courseStudentService, CourseLecturerService courseLecturerService, StudentService studentService) {
        this.courseStudentService = courseStudentService;
        this.courseLecturerService = courseLecturerService;
        this.studentService = studentService;
    }
    @GetMapping("/studentCourse/all")
    public String getAllCourseStudents(Model model)
    {
        var courseStudents=courseStudentService.getAllCourseStudents();
        model.addAttribute("courseStudents",courseStudents);
        return "CourseStudents";
    }

    @GetMapping("/view-performance-students/{id}")
    public String GetCourseStudent(@PathVariable Integer id,Model model)
    {
        var courseStudent=courseStudentService.getAllCourseStudents();
        if(id!=0) {
             courseStudent=courseStudent.stream().filter(courseStudent1 -> courseStudent1.getCourseLecturer().getId() == id).toList();
        }
        model.addAttribute("courseStudent",courseStudent);
        return "view-performance-students";
    }
    //viewcourse-enrolment.html
    @GetMapping("/studentCourse/courseLecturer/{id}/{c_id}")
    public String GetAllCourseStudentByCourseLecturerId(@PathVariable Integer id, Model model, @PathVariable Integer c_id)
    {
        var courseStudents=courseStudentService.getAllCourseStudents().stream().
                filter(courseStudent1 ->  courseStudent1.getRequestStatus()<3);
        if(id!=0)
        {
            courseStudents=courseStudents.filter(courseStudent1 -> courseStudent1.getCourseLecturer().getId()==id );
        }
        List<CourseStudent> courseStudent=new ArrayList<>();
        if(c_id!=0)
        {
            courseStudent=courseStudents.filter(courseStudent1 -> courseStudent1.getCourseLecturer().getCourse().getId()==id).toList();
        }
        else courseStudent=courseStudents.toList();
        model.addAttribute("courseStudents",courseStudent);
        //ystem.out.println(courseStudent);
        return "viewcourse-enrolment";
    }
//for grade student
    @GetMapping("/grade-course-students/{id}")
    public String GetAllCourseStudentByCourseLecturerId2(@PathVariable Integer id,Model model)
    {
        var courseStudent=courseStudentService.getAllCourseStudents().stream().
                filter(courseStudent1 -> courseStudent1.getCourseLecturer().getId()==id).toList();
        model.addAttribute("courseStudents",courseStudent);
        //System.out.println(courseStudent);
        return "grade-course-students";
    }
    @PostMapping("/studentCourse/update/{id}")
    public String updateCourseStudent(@RequestBody CourseStudent courseStudent, @PathVariable Integer id, Model model)
    {
        var status=courseStudentService.updateCourseStudentById(id,courseStudent);
        model.addAttribute("status",status);
        return "courseStudents";
    }


    @PostMapping("/updateStudentGrade/{id}")
    public String updateStudentCourse(@PathVariable Integer id,@RequestBody String body,Model model)
    {
        System.out.println(body);
        Double grade=Double.valueOf(body.substring(6));
        var courseStudent=courseStudentService.getCourseStudentById(id);
        courseStudent.setGrade(grade);
        courseStudent.setRequestStatus(3);

        var courseStudents=courseStudentService.getAllCourseStudents().stream().filter(courseStudent1 -> courseStudent1.getStudent().getId()==id).toList();
        double totol=0.0;
        double credit=0.0;
        for(var c:courseStudents)
        {
            totol+=c.getGrade()*c.getCourseLecturer().getCourse().getCourseCredits();
            credit+=c.getCourseLecturer().getCourse().getCourseCredits();
        }
        studentService.getStudentById(id).setGpa((float) (totol/credit));
        //model.addAttribute("updatedStudent",courseStudent);
        return "redirect:/grade-course-students/"+courseStudent.getCourseLecturer().getId();
    }

    @GetMapping("/studentEnrolling/{studentId}/{courseLecturerId}")
    public String createCourseStudent(Model model, @PathVariable Integer courseLecturerId, @PathVariable Integer studentId)
    {
        CourseLecturer courseLecturer=courseLecturerService.getCourseLecturerById(courseLecturerId);
        CourseStudent courseStudent=new CourseStudent();
        courseStudent.setCourseLecturer(courseLecturer);
        courseStudent.setStudent(studentService.getStudentById(studentId));
        courseStudent.setRequestStatus(0);
        courseStudentService.createCourseStudentById(courseStudent);
        boolean status=true;
        if(courseLecturer.getCourseCapacity()>courseLecturer.getEnrolled()){
            status=courseStudentService.createCourseStudentById(courseStudent);
        }
        else status =false;
        model.addAttribute("status",status);
        return "/studentIndex";
    }

//    @GetMapping ("/StudentCourse/delete/{id}")
//    public String deleteCourseStudent(@PathVariable Integer id,Model model)
//    {
//        var status=courseStudentService.deleteCourseStudentById(id);
//        model.addAttribute("status",status);
//        return "courseStudents";
//    }
}
