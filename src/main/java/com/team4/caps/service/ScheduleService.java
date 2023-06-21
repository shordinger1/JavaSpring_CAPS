package com.team4.caps.service;

import com.team4.caps.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.team4.caps.repository.ScheduleRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }



    public List<Schedule> getAllSchedules()
    {
        return scheduleRepository.findAll();
    }

    public Schedule getScheduleById(Integer id)
    {
        return scheduleRepository.findById(id).orElseThrow();
    }

    public Boolean deleteScheduleById(Integer id)
    {
        try{
            var Schedule=scheduleRepository.findById(id).orElseThrow();
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
        scheduleRepository.deleteById(id);
        return true;
    }

    public boolean updateScheduleById(Integer id,Schedule updatedSchedule)
    {
        Schedule Schedule;
        try{
            Schedule=scheduleRepository.findById(id).orElseThrow();
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
        Schedule=updatedSchedule;
        Schedule.setId(id);
        scheduleRepository.save(Schedule);
        return true;
    }



}