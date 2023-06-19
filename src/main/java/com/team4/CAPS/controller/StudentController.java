package com.team4.caps.controller;

import com.team4.caps.model.Student;
import com.team4.caps.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("/all")
    public String getAllStudents(Model model)
    {
        var students=studentService.getAllStudents();
        model.addAttribute("students",students);
        return "students";
    }

    @GetMapping("/{id}")
    public String GetOneStudent(@PathVariable Integer id,Model model)
    {
        var student=studentService.getStudentById(id);
        model.addAttribute("Student_"+id.toString(),student);
        return "student";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@RequestBody Student student, @PathVariable Integer id, Model model)
    {
        var status=studentService.updateStudentById(id,student);
        model.addAttribute("status",status);
        return "students";
    }

    @GetMapping ("delete/{id}")
    public String deleteStudent(@PathVariable Integer id,Model model)
    {
        var status=studentService.deleteStudentById(id);
        model.addAttribute("status",status);
        return "students";
    }

}
