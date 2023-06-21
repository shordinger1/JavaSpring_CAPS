package com.team4.caps.service;

import com.team4.caps.model.Faculty;
import com.team4.caps.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public List<Faculty> getAllFacultys()
    {
        return facultyRepository.findAll();
    }

    public Faculty getFacultyById(Integer id)
    {
        return facultyRepository.findById(id).orElseThrow();
    }

    public Boolean deleteFacultyById(Integer id)
    {
        try{
            var Faculty=facultyRepository.findById(id).orElseThrow();
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
        facultyRepository.deleteById(id);
        return true;
    }

    public boolean updateFacultyById(Integer id,Faculty updatedFaculty)
    {
        Faculty Faculty;
        try{
            Faculty=facultyRepository.findById(id).orElseThrow();
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
        Faculty=updatedFaculty;
        Faculty.setId(id);
        facultyRepository.save(Faculty);
        return true;
    }



}

