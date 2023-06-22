package com.team4.caps.controller;

import com.team4.caps.service.AdminService;
import com.team4.caps.service.LecturerService;
import com.team4.caps.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Controller
@RequestMapping("/admins")
public class AdminController {
    private final AdminService adminService;

    private final StudentService studentService;

    private final LecturerService lecturerService;


    @Autowired
    public AdminController(AdminService adminService, StudentService studentService, LecturerService lecturerService) {
        this.adminService = adminService;
        this.studentService = studentService;
        this.lecturerService = lecturerService;
    }

    @PostMapping("login")
    public String login(@RequestBody String username, @RequestBody String password, @RequestBody String type, HttpSession session)
    {
        switch (type)
        {
            case "Admin":
            {
                var admin=adminService.getAllAdmins().stream().map(admin1-> username.equals(admin1.getUsername())&&
                        password.equals(admin1.getPassword())).count();
                if(admin==1)
                {
                    session.setAttribute("username",username);
                    session.setAttribute("type",type);
                }
                return "index";

            }
            case "Student":
            {
                var student=studentService.getAllStudents().stream().map(admin1-> username.equals(admin1.getUsername())&&
                        password.equals(admin1.getPassword())).count();
                if(student==1)
                {
                    session.setAttribute("username",username);
                    session.setAttribute("type",type);
                }
                return "index";
            }
            case "Lecturer":
            {
                var lecturer=lecturerService.getAllLecturers().stream().map(admin1-> username.equals(admin1.getUsername())&&
                        password.equals(admin1.getPassword())).count();
                if(lecturer==1)
                {
                    session.setAttribute("username",username);
                    session.setAttribute("type",type);
                }
                return "index";
            }
            default:
            {
                Random random=new Random();
                session.setAttribute("username","guest"+ random.nextInt());
                session.setAttribute("type","guest");
                return "error";
            }
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session)
    {
        Random random=new Random();
        session.setAttribute("username","guest"+ random.nextInt());
        session.setAttribute("type","guest");
        return "index";
    }

}
