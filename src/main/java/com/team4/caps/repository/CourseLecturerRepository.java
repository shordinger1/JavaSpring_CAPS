package com.team4.caps.repository;

import com.team4.caps.model.CourseLecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseLecturerRepository extends JpaRepository<CourseLecturer, Integer> {

    @Query("select concat(cl.lecturer.firstname,' ', cl.lecturer.lastname) from CourseLecturer cl where cl.course.id = :id")
    List<String> queryLecturerNamesByCourseId(@Param("id") Integer id);

    @Query("SELECT cl FROM CourseLecturer cl WHERE cl.course.id = :id")
    CourseLecturer getCourseLecturerByCourseId(@Param("id") int id);
}