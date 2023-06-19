package com.team4.caps.controller;

import com.team4.caps.model.CourseLecturer;
import com.team4.caps.service.CourseLecturerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/course-Lecturers")
public class CourseLecturerController {

    private final CourseLecturerService courseLecturerService;

    @Autowired
    public CourseLecturerController(CourseLecturerService courseLecturerService) {
        this.courseLecturerService = courseLecturerService;
    }
    @GetMapping("/all")
    public String getAllCourseLecturers(Model model)
    {
        var courseLecturers=courseLecturerService.getAllCourseLecturers();
        model.addAttribute("courseLecturers",courseLecturers);
        return "CourseLecturers";
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

    @GetMapping ("delete/{id}")
    public String deleteCourseLecturer(@PathVariable Integer id,Model model)
    {
        var status=courseLecturerService.deleteCourseLecturerById(id);
        model.addAttribute("status",status);
        return "courseLecturers";
    }
}
