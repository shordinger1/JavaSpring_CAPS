package com.team4.caps.service;

import com.team4.caps.model.Lecturer;
import com.team4.caps.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LecturerService {
    private final LecturerRepository lecturerRepository;

    @Autowired
    public LecturerService(LecturerRepository lecturerRepository) {
        this.lecturerRepository = lecturerRepository;
    }


    public List<Lecturer> getAllLecturers()
    {
        return lecturerRepository.findAll();
    }

    public Lecturer getLecturerById(Integer id)
    {
        return lecturerRepository.findById(id).orElseThrow();
    }

    public Boolean deleteLecturerById(Integer id)
    {
        try{
            var Lecturer=lecturerRepository.findById(id).orElseThrow();
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
        lecturerRepository.deleteById(id);
        return true;
    }

    public boolean updateLecturerById(Integer id,Lecturer updatedLecturer)
    {
        Lecturer Lecturer;
        try{
            Lecturer=lecturerRepository.findById(id).orElseThrow();
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
        Lecturer=updatedLecturer;
        Lecturer.setId(id);
        lecturerRepository.save(Lecturer);
        return true;
    }



}