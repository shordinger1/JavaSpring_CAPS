package com.team4.caps.service;

import com.team4.caps.model.Course;
import com.team4.caps.model.CourseLecturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team4.caps.repository.CourseLecturerRepository;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CourseLecturerService {

    private final CourseLecturerRepository courseLecturerRepository;

    @Autowired
    public CourseLecturerService(CourseLecturerRepository courseLecturerRepository) {
        this.courseLecturerRepository = courseLecturerRepository;
    }


    public List<CourseLecturer> getAllCourseLecturers()
    {
        return courseLecturerRepository.findAll();
    }

    public CourseLecturer getCourseLecturerById(Integer id)
    {
        return courseLecturerRepository.findById(id).orElseThrow();
    }

    public Boolean deleteCourseLecturerById(Integer id)
    {
        try{
            var courseLecturer=courseLecturerRepository.findById(id).orElseThrow();
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
        courseLecturerRepository.deleteById(id);
        return true;
    }

    public boolean updateCourseLecturerById(Integer id,CourseLecturer updatedCourseLecturer)
    {
        CourseLecturer courseLecturer;
        try{
            courseLecturer=courseLecturerRepository.findById(id).orElseThrow();
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
        courseLecturer=updatedCourseLecturer;
        courseLecturer.setId(id);
        courseLecturerRepository.save(courseLecturer);
        return true;
    }
}
