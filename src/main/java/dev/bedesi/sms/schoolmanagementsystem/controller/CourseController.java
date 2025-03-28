package dev.bedesi.sms.schoolmanagementsystem.controller;

import dev.bedesi.sms.schoolmanagementsystem.mysql.entity.CourseEntity;
import dev.bedesi.sms.schoolmanagementsystem.mysql.entity.StudentCourseEntity;
import dev.bedesi.sms.schoolmanagementsystem.service.CourseService;
import dev.bedesi.sms.schoolmanagementsystem.service.StudentCourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/course")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentCourseService studentCourseService;

    @GetMapping
    public List<CourseEntity> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseEntity> getCourseById(@PathVariable int id) {
        return courseService.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public CourseEntity createCourse(@RequestBody CourseEntity course) {
        return courseService.createCourse(course);
    }

    @PostMapping("/assign-teacher")
    public ResponseEntity<?> assignTeacher(@RequestBody CourseEntity course) {
        try {
            CourseEntity updatedCourse = courseService.assignTeacher(course);
            return ResponseEntity.ok(updatedCourse); // 200 OK with the updated course on success
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND) // 404 status
                    .body("course or teacher does not exist"); // Custom message
        }
    }

    @PostMapping("/assign-student")
    public ResponseEntity<?> assignStudent(@RequestBody StudentCourseEntity studentCourse) {
        try {
            StudentCourseEntity studentCourseEntity = courseService.assignStudent(studentCourse);
            return ResponseEntity.ok(studentCourseEntity); // 200 OK with the updated course on success
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND) // 404 status
                    .body("student or course does not exist"); // Custom message
        }
    }
}