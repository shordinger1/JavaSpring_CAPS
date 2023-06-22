package com.team4.caps.service;

import com.team4.caps.model.Student;
import com.team4.caps.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public List<Student> getAllStudents()
    {
        return studentRepository.findAll();
    }

    public Student getStudentById(Integer id)
    {
        return studentRepository.findById(id).orElseThrow();
    }

    public Boolean deleteStudentById(Integer id)
    {
        try{
            var Student=studentRepository.findById(id).orElseThrow();
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }

    public boolean updateStudentById(Integer id,Student updatedStudent)
    {
        Student Student;
        try{
            Student=studentRepository.findById(id).orElseThrow();
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
        Student=updatedStudent;
        Student.setId(id);
        studentRepository.save(Student);
        return true;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Integer id, Student updatedStudent) throws Exception {
        if(studentRepository.existsById(id)) {
            var student = updatedStudent;
            student.setId(id);
            return studentRepository.save(student);
        }
        return null;
    }


}

