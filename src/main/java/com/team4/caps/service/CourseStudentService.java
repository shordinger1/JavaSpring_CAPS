package com.team4.caps.service;

import com.team4.caps.model.CourseLecturer;
import com.team4.caps.model.CourseStudent;
import com.team4.caps.repository.CourseLecturerRepository;
import com.team4.caps.repository.CourseStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseStudentService {

    private final CourseStudentRepository courseStudentRepository;
    private final CourseLecturerService courseLecturerService;

    @Autowired
    public CourseStudentService(CourseStudentRepository courseStudentRepository, CourseLecturerService courseLecturerService) {
        this.courseStudentRepository = courseStudentRepository;
        this.courseLecturerService = courseLecturerService;
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
        if(courseStudentRepository.existsById(id)) {
            courseStudentRepository.deleteById(id);
        }
        return true;
    }

    public boolean updateCourseStudentById(Integer id,CourseStudent updatedCourseStudent)
    {
        CourseStudent courseStudent;
        if(courseStudentRepository.existsById(id)) {
            courseStudent = updatedCourseStudent;
            courseStudent.setId(id);
            courseStudentRepository.save(courseStudent);
            return true;
        }
        return false;
    }

    public boolean createCourseStudentById(Integer id, CourseStudent courseStudent) {
        if(courseStudentRepository.existsById(id)){
            return false;
        }
        Integer courseLecturerId=courseStudent.getCourseLecturer().getId();
        CourseLecturer courseLecturer=courseLecturerService.getCourseLecturerById(courseLecturerId);
        courseLecturer.setEnrolled(courseLecturer.getEnrolled()+1);
        courseLecturerService.updateCourseLecturerById(courseLecturerId,courseLecturer);
        courseStudentRepository.save(courseStudent);
        return true;
    }
}
