package com.team4.caps.service;

import com.team4.caps.model.Course;
import com.team4.caps.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    public List<Course> getAllCourses()
    {
        return courseRepository.findAll();
    }

    public Course getCourseById(Integer id)
    {
        return courseRepository.findById(id).orElseThrow();
    }

    public Boolean deleteCourseById(Integer id)
    {
        if(courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
        }
        return true;
    }

    public void createCourse(Course course) {
        courseRepository.save(course);
    }

    public boolean updateCourseById(Integer id,Course updatedCourse)
    {
        Course course;
        if(courseRepository.existsById(id)) {
            course = updatedCourse;
            course.setId(id);
            courseRepository.save(course);
            return true;
        }
        return false;
    }







}

