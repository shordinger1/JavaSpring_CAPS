package com.team4.caps.controller;

import com.team4.caps.model.CourseSchedule;
import com.team4.caps.service.CourseScheduleService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/course-Schedules")
public class CourseScheduleController {

    private final CourseScheduleService courseScheduleService;

    @Autowired
    public CourseScheduleController(CourseScheduleService courseScheduleService) {
        this.courseScheduleService = courseScheduleService;
    }
    @GetMapping("/all")
    public String getAllCourseSchedules(Model model)
    {
        var courseSchedules=courseScheduleService.getAllCourseSchedules();
        model.addAttribute("courseSchedules",courseSchedules);
        return "CourseSchedules";
    }

    @GetMapping("/{id}")
    public String GetOneCourseSchedule(@PathVariable Integer id,Model model)
    {
        var courseSchedule=courseScheduleService.getCourseScheduleById(id);
        model.addAttribute("courseSchedule_"+id.toString(),courseSchedule);
        return "courseSchedule";
    }

    @PostMapping("/update/{id}")
    public String updateCourseSchedule(@RequestBody CourseSchedule courseSchedule, @PathVariable Integer id, Model model)
    {
        var status=courseScheduleService.updateCourseScheduleById(id,courseSchedule);
        model.addAttribute("status",status);
        return "courseSchedules";
    }

    @GetMapping ("delete/{id}")
    public String deleteCourseSchedule(@PathVariable Integer id,Model model)
    {
        var status=courseScheduleService.deleteCourseScheduleById(id);
        model.addAttribute("status",status);
        return "courseSchedules";
    }
}
