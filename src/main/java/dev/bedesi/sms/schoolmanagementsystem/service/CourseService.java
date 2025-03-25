package dev.bedesi.sms.schoolmanagementsystem.service;

import dev.bedesi.sms.schoolmanagementsystem.entity.Course;
import dev.bedesi.sms.schoolmanagementsystem.entity.Teacher;
import dev.bedesi.sms.schoolmanagementsystem.repository.CourseRepository;
import dev.bedesi.sms.schoolmanagementsystem.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Course createCourse(Course course) {
        if (course.getTeacher() != null && course.getTeacher().getId() != null) {
            Optional<Teacher> teacher = teacherRepository.findById(course.getTeacher().getId());
            teacher.ifPresent(course::setTeacher);
        }
        return courseRepository.save(course);
    }
}