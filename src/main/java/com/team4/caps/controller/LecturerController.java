package com.team4.caps.controller;

import com.team4.caps.config.SecurityConfig;
import com.team4.caps.model.Lecturer;
import com.team4.caps.model.Person;
import com.team4.caps.model.Student;
import com.team4.caps.service.LecturerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
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

    @GetMapping("lecturer/add")
    public String redirectAddLecturer(Model model)
    {
        model.addAttribute("lecturer",new Lecturer());
        //model.addAttribute("header","Content-Type:application/json;charset=UTF-8");
        return "lecturerform";
    }

    @GetMapping("lecturer/edit/{id}")
    public String redirectEditLecturer(@PathVariable Integer id,Model model)
    {
        var lecturer=lecturerService.getLecturerById(id);
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

    @GetMapping("lecturer/profile/{id}")
    public String GetOneLecturer2(@PathVariable Integer id,Model model)
    {
        var lecturer=lecturerService.getLecturerById(id);
        //Lecturer.setPassword("000");
        model.addAttribute("lecturer",lecturer);
        return "lecturerProfile";
    }

    //@PostMapping("")
    @PostMapping(value="/addLecturer")
    public String createLecturer(@Valid  @ModelAttribute Lecturer lecturer,BindingResult result,Model model)
    {
        if(result.hasErrors())
        {
            return "lecturerform";
        }
        System.out.println("coming in!");
        lecturer.setPassword(SecurityConfig.encoder("123456"));
        var status=lecturerService.createLecturer(lecturer);
        model.addAttribute("status",status);
        List<Lecturer> lecturers=new ArrayList<>();
        lecturers.add(lecturer);
        model.addAttribute("lecturers",lecturers);
        return "lecturers";
    }

    @PostMapping("/lecturer/update/{id}")
    public String updateLecturer(@Valid @ModelAttribute Lecturer lecturer, BindingResult result, @PathVariable Integer id, Model model) throws IllegalAccessException {
        if(result.hasErrors())
        {
            return "lecturer-edit";
        }
        Lecturer lecturerBefore=lecturerService.getLecturerById(id);
        lecturer.setPassword(SecurityConfig.encoder(lecturer.getPassword()));
        List<Field> fieldList = new ArrayList<>();
        fieldList.addAll(List.of(Lecturer.class.getDeclaredFields()));
        fieldList.addAll(List.of(Person.class.getDeclaredFields()));
        for(var param:fieldList)
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

    @GetMapping ("/lecturer/delete/{id}")
    public String deleteLecturer(@PathVariable Integer id,Model model)
    {
        var status=lecturerService.deleteLecturerById(id);
        var lecturers=lecturerService.getAllLecturers();
        //model.addAttribute("status",status);
        model.addAttribute("lecturers",lecturers);
        return "lecturers";
    }

}


