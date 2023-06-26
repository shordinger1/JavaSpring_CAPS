package com.team4.caps.repository;

import com.team4.caps.domain.dto.CourseDTO;
import com.team4.caps.domain.dto.PageDataDTO;
import com.team4.caps.domain.param.SearchCourseParam;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CourseSelectionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    //CourseDTO(Integer id, String courseName, Date courseStartDate, Date courseEndDate,
    // Integer courseCredits, Integer courseCapacity, Integer courseVacancy, String facultyName, String lecturerName
    public PageDataDTO<List<CourseDTO>> findCourseByMultipleCondition(SearchCourseParam searchCourseParam) {
        String jpql = "select distinct new com.team4.caps.domain.dto.CourseDTO(" +
                "cl.course.id, cl.course.courseName, " +
                "cl.schedule.scheduleStartTime, cl.schedule.scheduleEndTime, " +
                "cl.course.courseCredits, cl.classroom.classRoomSize, cl.classroom.classRoomVacancy, cl.course.faculty.facultyName) "+
                "from CourseLecturer cl  where  cl.classroom.classRoomVacancy > 0 ";

        Map<String, Object> params = new HashMap<>();
        if (!ObjectUtils.isEmpty(searchCourseParam.getCourseName())) {
            jpql += " and cl.course.courseName = :courseName";
            params.put("courseName",searchCourseParam.getCourseName());
        }
        if (!ObjectUtils.isEmpty(searchCourseParam.getLecturerName())) {
            jpql += " and concat(cl.lecturer.firstName,' ', cl.lecturer.lastName) like :lecturerName";
            params.put("lecturerName", "%" + searchCourseParam.getLecturerName() + "%");
        }
        if (!ObjectUtils.isEmpty(searchCourseParam.getCourseStartDate())) {
            jpql += " and cl.schedule.scheduleStartTime >= :courseStartDate";
            params.put("courseStartDate", searchCourseParam.getCourseStartDate());
        }
        if (!ObjectUtils.isEmpty(searchCourseParam.getCourseEndDate())) {
            jpql += " and cl.schedule.scheduleEndTime <= :courseEndDate";
            params.put("courseEndDate",searchCourseParam.getCourseEndDate());
        }
        TypedQuery<CourseDTO> query = entityManager.createQuery(jpql, CourseDTO.class);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }


        query.setFirstResult((searchCourseParam.getPageNumber()-1) * searchCourseParam.getPageSize());
        query.setMaxResults(searchCourseParam.getPageSize());

        //String countJpql = "select count(1)  from  "+jpql.substring(jpql.indexOf(" from ")) +"";
        String countJpql = "select count(1)  from Course where id in (select distinct cl.course.id "+jpql.substring(jpql.indexOf(" from ")) +")";

        Query countQuery = entityManager.createQuery(countJpql);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }

        Long count = (Long)countQuery.getSingleResult();

        PageDataDTO<List<CourseDTO>> pageDataDTO = new PageDataDTO<>();
        pageDataDTO.setPageData(query.getResultList());
        pageDataDTO.setTotalPage(count.intValue(), searchCourseParam.getPageSize());

        return pageDataDTO;
    }

}
