package dev.bedesi.sms.schoolmanagementsystem.service;

import dev.bedesi.sms.schoolmanagementsystem.mysql.entity.CourseEntity;
import dev.bedesi.sms.schoolmanagementsystem.mysql.entity.StudentEntity;
import dev.bedesi.sms.schoolmanagementsystem.mysql.entity.TeacherEntity;
import dev.bedesi.sms.schoolmanagementsystem.mysql.repository.CourseRepository;
import dev.bedesi.sms.schoolmanagementsystem.mysql.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    public List<CourseEntity> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<CourseEntity> getCourseById(int id) {
        return courseRepository.findById(id);
    }

    public CourseEntity createCourse(CourseEntity course) {
        return courseRepository.save(course);
    }

    public CourseEntity assignTeacher(CourseEntity course) {
        Objects.requireNonNull(course, "Course cannot be null");
        CourseEntity existingCourse = courseRepository.findById(course.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Course with ID " + course.getId() + " not found or already inactive"));

        TeacherEntity teacher = teacherRepository.findById(course.getTeacher().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Teacher with ID " + course.getTeacher().getId() + " not found or already inactive"));

        existingCourse.setTeacher(teacher);
        return courseRepository.save(existingCourse);
    }
}