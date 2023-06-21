package com.team4.caps.controller.Admin;

import com.team4.caps.model.Course;
import com.team4.caps.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admincourse")
public class AdminCourseController {
    private final CourseService courseService;

    @Autowired
    public AdminCourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<com.team4.caps.model.Course>> getAllCourses() {
        List<com.team4.caps.model.Course> courses = courseService.getAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Integer id) throws Exception {
        Course course = courseService.getCourseById(id);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course newCourse) {
        Course addedCourse = courseService.createCourse(newCourse);
        return new ResponseEntity<>(addedCourse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable int id, @RequestBody Course updatedCourse) throws Exception {
        Course updatedCourseEntity = courseService.updateCourse(id, updatedCourse);
        return new ResponseEntity<>(updatedCourseEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int id) {
        courseService.deleteCourseById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

