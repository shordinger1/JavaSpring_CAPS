package com.team4.caps.repository;

import com.team4.caps.model.CourseLecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseLecturerRepository extends JpaRepository<CourseLecturer, Integer> {

}