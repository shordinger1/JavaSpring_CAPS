package com.team4.caps.service;


import com.team4.caps.model.CourseSchedule;
import com.team4.caps.repository.CourseScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CourseScheduleService {

    private final CourseScheduleRepository courseScheduleRepository;

    @Autowired
    public CourseScheduleService(CourseScheduleRepository courseScheduleRepository) {
        this.courseScheduleRepository = courseScheduleRepository;
    }

    public List<CourseSchedule> getAllCourseSchedules() {
        return courseScheduleRepository.findAll();
    }

    public CourseSchedule getCourseScheduleById(Integer id) {
        return courseScheduleRepository.findById(id).orElseThrow();
    }

    public Boolean deleteCourseScheduleById(Integer id) {
        if(courseScheduleRepository.existsById(id)) {
            courseScheduleRepository.deleteById(id);
        }
        return true;
    }

    public boolean updateCourseScheduleById(Integer id, CourseSchedule updatedCourseSchedule) {
        CourseSchedule courseSchedule;
        if(courseScheduleRepository.existsById(id)) {
            courseSchedule = updatedCourseSchedule;
            courseSchedule.setId(id);
            courseScheduleRepository.save(courseSchedule);
            return true;
        }
        return false;
    }

    public void createCourseSchedule(CourseSchedule courseSchedule) {
        courseScheduleRepository.save(courseSchedule);
    }
}
