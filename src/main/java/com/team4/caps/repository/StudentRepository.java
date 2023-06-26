package com.team4.caps.repository;


import com.team4.caps.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
    @Query("SELECT s FROM Student s WHERE s.username = :username")
    Student findStudentByUsername(@Param("username") String username);
}
