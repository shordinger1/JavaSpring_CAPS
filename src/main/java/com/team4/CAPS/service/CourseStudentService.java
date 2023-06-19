package com.team4.caps.service;

import com.team4.caps.model.CourseStudent;
import com.team4.caps.repository.CourseStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CourseStudentService {

    private final CourseStudentRepository courseStudentRepository;

    @Autowired
    public CourseStudentService(CourseStudentRepository courseStudentRepository) {
        this.courseStudentRepository = courseStudentRepository;
    }
    public List<CourseStudent> getAllCourseStudents()
    {
        return courseStudentRepository.findAll();
    }

    public CourseStudent getCourseStudentById(Integer id)
    {
        return courseStudentRepository.findById(id).orElseThrow();
    }

    public Boolean deleteCourseStudentById(Integer id)
    {
        try{
            var CourseStudent=courseStudentRepository.findById(id).orElseThrow();
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
        courseStudentRepository.deleteById(id);
        return true;
    }

    public boolean updateCourseStudentById(Integer id,CourseStudent updatedCourseStudent)
    {
        CourseStudent courseStudent;
        try{
            courseStudent=courseStudentRepository.findById(id).orElseThrow();
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
        courseStudent=updatedCourseStudent;
        courseStudent.setId(id);
        courseStudentRepository.save(courseStudent);
        return true;
    }
}
