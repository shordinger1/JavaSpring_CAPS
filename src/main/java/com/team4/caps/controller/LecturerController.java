package com.team4.caps.controller;

import com.team4.caps.config.SecurityConfig;
import com.team4.caps.model.Lecturer;
import com.team4.caps.model.Student;
import com.team4.caps.service.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class LecturerController {
    private final LecturerService lecturerService;

    @Autowired
    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }


    @GetMapping("/lecturers")
    public String getAllLecturers(Model model)
    {
        var lecturers=lecturerService.getAllLecturers();
        //lecturers.forEach(Lecturer -> Lecturer.setPassword("000"));
        model.addAttribute("lecturers",lecturers);
        return "lecturers";
    }

    @GetMapping("Lecturer/add")
    public String redirectAddLecturer(Model model)
    {
        model.addAttribute("lecturer",new Lecturer());
        //model.addAttribute("header","Content-Type:application/json;charset=UTF-8");
        return "lecturerform";
    }

//lecturer admin update
    @GetMapping("lecturer/update/{id}")
    public String redirectUpdateLecturer(@PathVariable Integer id,Model model)
    {
        Lecturer lecturer=lecturerService.getLecturerById(id);
        model.addAttribute("lecturer",lecturer);
        //model.addAttribute("header","Content-Type:application/json;charset=UTF-8");
        return "lecturer_information";
    }

    //lecturer personal update
    @GetMapping("lecturer/edit/{id}")
    public String redirectEditLecturer(@PathVariable Integer id,Model model)
    {
        Lecturer lecturer=lecturerService.getLecturerById(id);
        model.addAttribute("lecturer",lecturer);
        //model.addAttribute("header","Content-Type:application/json;charset=UTF-8");
        return "lecturer-edit";
    }


    @GetMapping("lecturer/{id}")
    public String GetOneLecturer(@PathVariable Integer id,Model model)
    {
        var lecturer=lecturerService.getLecturerById(id);
        List<Lecturer> lecturers=new ArrayList<>();
        lecturers.add(lecturer);
        //Lecturer.setPassword("000");
        model.addAttribute("lecturers",lecturers);
        return "lecturers";
    }

    //@PostMapping("")
    @PostMapping(value="/addLecturer",produces = "application/json")
    public String createLecturer(@ModelAttribute Lecturer lecturer,Model model)
    {
        //System.out.println("114514");
        //Lecturer Lecturer= (Lecturer) model.getAttribute("Lecturer");
        lecturer.setPassword(SecurityConfig.encoder(lecturer.getPassword()));
        Date date=new Date();
        //lecturer.setEnrollmentDate((date.getTime()));
        var status=lecturerService.createLecturer(lecturer);
        model.addAttribute("status",status);
        List<Lecturer> lecturers=new ArrayList<>();
        lecturers.add(lecturer);
        model.addAttribute("lecturers",lecturers);
        return "lecturers";
    }

    @PostMapping("lecturer/edit/{id}")
    public String updateLecturer(@ModelAttribute Lecturer lecturer, @PathVariable Integer id, Model model) throws IllegalAccessException {
        Lecturer lecturerBefore=lecturerService.getLecturerById(id);
        lecturer.setPassword(SecurityConfig.encoder(lecturer.getPassword()));
        for(var param:Lecturer.class.getDeclaredFields())
        {
            param.setAccessible(true);
            if(param.get(lecturer)!=null) {
                param.set(lecturer, param.get(lecturerBefore));
            }
        }
        var status=lecturerService.updateLecturerById(id,lecturer);
        List<Lecturer> lecturers=new ArrayList<>();
        lecturers.add(lecturer);
        model.addAttribute("lecturers",lecturers);
        return "lecturers";
    }

    @GetMapping ("lecturer/delete/{id}")
    public String deleteLecturer(@PathVariable Integer id,Model model)
    {
        var status=lecturerService.deleteLecturerById(id);
        var lecturers=lecturerService.getAllLecturers();
        //model.addAttribute("status",status);
        model.addAttribute("lecturers",lecturers);
        return "lecturers";
    }

}


