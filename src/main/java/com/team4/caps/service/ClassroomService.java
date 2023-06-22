package com.team4.caps.service;


import com.team4.caps.model.Classroom;
import com.team4.caps.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomService {
    private ClassroomRepository classroomRepository;

@Autowired
    public ClassroomService(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    public List<Classroom> getAllClassRoom()
    {
        return classroomRepository.findAll();
    }

}
