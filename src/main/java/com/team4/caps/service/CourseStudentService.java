package com.team4.caps.service;

import com.team4.caps.model.*;
import com.team4.caps.repository.ClassroomRepository;
import com.team4.caps.repository.CourseRepository;
import com.team4.caps.repository.CourseStudentRepository;
import com.team4.caps.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseStudentService {

    private final CourseStudentRepository courseStudentRepository;
    private final CourseLecturerService courseLecturerService;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ClassroomRepository classroomRepository;

    @Autowired
    public CourseStudentService(CourseStudentRepository courseStudentRepository,
                                CourseLecturerService courseLecturerService,
                                StudentRepository studentRepository,
                                CourseRepository courseRepository,
                                ClassroomRepository classroomRepository) {
        this.courseStudentRepository = courseStudentRepository;
        this.courseLecturerService = courseLecturerService;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.classroomRepository = classroomRepository;
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

    public boolean createCourseStudentById(CourseStudent courseStudent) {
        Integer courseLecturerId=courseStudent.getCourseLecturer().getId();
        CourseLecturer courseLecturer=courseLecturerService.getCourseLecturerById(courseLecturerId);
        courseLecturer.setEnrolled(courseLecturer.getEnrolled()+1);
        courseLecturerService.updateCourseLecturerById(courseLecturerId,courseLecturer);
        courseStudentRepository.save(courseStudent);
        return true;
    }

    @Transactional
    public CourseStudent createCourseStudentByUsernameAndCourseId(int courseId, HttpServletRequest request) throws Exception {
        String username = (String)request.getSession().getAttribute("username");
        Student student = studentRepository.findStudentByUsername(username);

        Course course = courseRepository.findById(courseId).get();
        CourseLecturer courseLecturer = courseLecturerService.getCourseLecturerByCourseId(courseId);
        Classroom classroom = courseLecturer.getClassroom();
        int vacancy = classroom.getClassRoomVacancy();
        vacancy -= 1;
        classroom.setClassRoomVacancy(vacancy);
        classroomRepository.save(classroom);

        CourseStudent courseStudent = new CourseStudent();
        courseStudent.setStudent(student);
        courseStudent.setCourseLecturer(courseLecturer);
        courseStudent.setRequestStatus(0);
        courseStudentRepository.save(courseStudent);
        return courseStudent;
    }

}
