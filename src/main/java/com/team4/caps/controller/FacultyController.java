package com.team4.caps.controller;

import com.team4.caps.model.Faculty;
import com.team4.caps.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    @Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }


    @GetMapping("/all")
    public String getAllFaculties(Model model) {
        var faculties = facultyService.getAllFacultys();
        model.addAttribute("faculties", faculties);
        return "faculties";
    }

    @GetMapping("/{id}")
    public String GetOneFaculty(@PathVariable Integer id, Model model) {
        var Faculty = facultyService.getFacultyById(id);
        model.addAttribute("Faculty_" + id.toString(), Faculty);
        return "faculty";
    }

    @PostMapping("/update/{id}")
    public String updateFaculty(@RequestBody Faculty faculty, @PathVariable Integer id, Model model) {
        var status = facultyService.updateFacultyById(id, faculty);
        model.addAttribute("status", status);
        return "faculties";
    }

    @GetMapping("delete/{id}")
    public String deleteFaculty(@PathVariable Integer id, Model model) {
        var status = facultyService.deleteFacultyById(id);
        model.addAttribute("status", status);
        return "faculties";
    }
}
