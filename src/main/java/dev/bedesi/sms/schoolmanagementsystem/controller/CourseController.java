package dev.bedesi.sms.schoolmanagementsystem.controller;

import dev.bedesi.sms.schoolmanagementsystem.entity.Course;
import dev.bedesi.sms.schoolmanagementsystem.entity.Teacher;
import dev.bedesi.sms.schoolmanagementsystem.repository.CourseRepository;
import dev.bedesi.sms.schoolmanagementsystem.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sms/course")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseRepository.findById(id);
        return course.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        // Ensure the teacher exists
        if (course.getTeacher() != null && course.getTeacher().getId() != null) {
            Optional<Teacher> teacher = teacherRepository.findById(course.getTeacher().getId());
            teacher.ifPresent(course::setTeacher);
        }
        return courseRepository.save(course);
    }
}
