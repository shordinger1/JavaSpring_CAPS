package com.team4.caps.service;

import com.team4.caps.domain.dto.CourseDTO;
import com.team4.caps.domain.dto.PageDataDTO;
import com.team4.caps.domain.param.SearchCourseParam;
import com.team4.caps.repository.CourseLecturerRepository;
import com.team4.caps.repository.CourseSelectionRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseSelectionService {

//    @Autowired
//    CourseSelectionRepository courseSelectionRepo;
//    @Autowired
//    CourseLecturerRepository courseLecturerRepository;
//
//    @Autowired
//    CourseService courseService;
//
//    @Autowired
//    EnrollmentRepository enrollmentRepository;
//
//    private CourseQueue queue;

    @Autowired
    CourseLecturerRepository courseLecturerRepository;
    @Autowired
    CourseStudentService courseStudentService;
    @Autowired
    CourseSelectionRepository courseSelectionRepository;

    public PageDataDTO<List<CourseDTO>> findCourseByMultipleCondition(SearchCourseParam searchCourseParam) {

        PageDataDTO<List<CourseDTO>> pageDataDTO = courseSelectionRepository.findCourseByMultipleCondition(searchCourseParam);
        for (CourseDTO courseDTO : pageDataDTO.getPageData()) {
            List<String> lecturerNames = courseLecturerRepository.queryLecturerNamesByCourseId(courseDTO.getId());
            courseDTO.setLecturerName(StringUtils.join(lecturerNames,","));
        }
        return pageDataDTO;
    }
/*    @Override
    public void saveEnrolledCourse(int courseId, String courseName, String lecturerName, Date courseStartDate, Date courseEndDate){
        List<CourseDTO> courseDTOs = courseSelectionRepo.findCourseByMultipleCondition(courseId, courseName, lecturerName, courseStartDate, courseEndDate);
        courseSelectionRepo.save(courseDTOs);
    };*/

    // add enrolled course to student enrolled course list
//    public List<Course> addEnrolledCourse(List<CourseDTO> courseDTOS) throws Exception {
//        List<Course> enrolledCourses = null;
//        for(CourseDTO courseDTO : courseDTOs) {
//            int id = courseDTO.getCourseId();
//            Course course = courseService.getCourseById(id);
//            enrolledCourses.add(course);
//        }
//        //return enrolledCourses;
//    }

//    public CourseDTO addEnrolledCourse(Model model, HttpSession session, int courseId, String courseName, String lecturerName, Date courseStartDate, Date courseEndDate) {
//        CourseDTO courseDTO = new CourseDTO();
//       // courseDTO.setCourseId(courseId);
//        //courseDTO.setCourseName(courseName);
////        courseDTO.setLecturerName(lecturerName);
////        courseDTO.setCourseStartDate(courseStartDate);
////        courseDTO.setCourseEndDate(courseEndDate);
//        //courseSelectionRepo.save(courseDTO);
//        return courseDTO;
//    }

}


