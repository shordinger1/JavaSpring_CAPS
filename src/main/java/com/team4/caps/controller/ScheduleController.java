package com.team4.caps.controller;

import com.team4.caps.model.Schedule;
import com.team4.caps.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }


    @GetMapping("/all")
    public String getAllSchedules(Model model) {
        var schedules = scheduleService.getAllSchedules();
        model.addAttribute("schedules", schedules);
        return "schedules";
    }

    @GetMapping("/{id}")
    public String GetOneSchedule(@PathVariable Integer id, Model model) {
        var schedule = scheduleService.getScheduleById(id);
        model.addAttribute("schedule_" + id.toString(), schedule);
        return "schedule";
    }

    @PostMapping("/update/{id}")
    public String updateSchedule(@RequestBody Schedule schedule, @PathVariable Integer id, Model model) {
        var status = scheduleService.updateScheduleById(id, schedule);
        model.addAttribute("status", status);
        return "schedules";
    }

    @GetMapping("delete/{id}")
    public String deleteSchedule(@PathVariable Integer id, Model model) {
        var status = scheduleService.deleteScheduleById(id);
        model.addAttribute("status", status);
        return "schedules";
    }
}
