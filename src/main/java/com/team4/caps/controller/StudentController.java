package com.team4.caps.controller;

import com.team4.caps.config.SecurityConfig;
import com.team4.caps.model.Person;
import com.team4.caps.model.Student;
import com.team4.caps.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("/students")
    public String getAllStudents(Model model)
    {
        var students=studentService.getAllStudents();
        students.forEach(student -> student.setPassword("000"));
        model.addAttribute("students",students);
        return "students";
    }

    @GetMapping("student/add")
    public String redirectAddStudent(Model model)
    {
        model.addAttribute("student",new Student());
        //model.addAttribute("header","Content-Type:application/json;charset=UTF-8");
        return "studentform";
    }


    @GetMapping("student/update/{id}")
    public String redirectEditStudent(@PathVariable Integer id,Model model)
    {
        Student student=studentService.getStudentById(id);
        model.addAttribute("student",student);
        //model.addAttribute("header","Content-Type:application/json;charset=UTF-8");
        return "student-edit";
    }

    @GetMapping("student/{id}")
    public String GetOneStudent(@PathVariable Integer id,Model model)
    {
        var student=studentService.getStudentById(id);
        List<Student> students=new ArrayList<>();
        students.add(student);
        //student.setPassword("000");
        model.addAttribute("students",students);
        return "students";
    }

    //@PostMapping("")
    @PostMapping(value="/addstudent",produces = "application/json")
    public String createStudent(@ModelAttribute Student student,Model model)
    {
        //System.out.println("114514");
        //Student student= (Student) model.getAttribute("student");
        student.setPassword(SecurityConfig.encoder("123456"));
        Date date=new Date();
        student.setEnrollmentDate((date.getTime()));
        var status=studentService.createStudent(student);
        model.addAttribute("status",status);
        List<Student> students=new ArrayList<>();
        students.add(student);
        model.addAttribute("students",students);
        return "students";
    }

    @PostMapping("student/edit/{id}")
    public String updateStudent(@ModelAttribute Student student, @PathVariable Integer id, Model model) throws IllegalAccessException {
        var studentBefore=studentService.getStudentById(id);
        List<Field> fieldList = new ArrayList<>();
        fieldList.addAll(List.of(Student.class.getDeclaredFields()));
        fieldList.addAll(List.of(Person.class.getDeclaredFields()));
        for(var param:fieldList)
        {
            param.setAccessible(true);
            if(param.get(student)!=null&&param.get(student)!=param.get(studentBefore)) {
                param.set(studentBefore, param.get(student));
            }
        }
        //student.setPassword(SecurityConfig.encoder(student.getPassword()));
        System.out.println(student);
        var status=studentService.updateStudentById(id,studentBefore);
        List<Student> students=new ArrayList<>();
        students.add(studentBefore);
        model.addAttribute("students",students);
        return "students";
    }

    @GetMapping ("student/delete/{id}")
    public String deleteStudent(@PathVariable Integer id,Model model)
    {
        var status=studentService.deleteStudentById(id);
        var students=studentService.getAllStudents();
        //model.addAttribute("status",status);
        model.addAttribute("students",students);
        return "students";
    }

}
