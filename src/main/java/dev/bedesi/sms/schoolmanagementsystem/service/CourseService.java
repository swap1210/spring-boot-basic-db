package dev.bedesi.sms.schoolmanagementsystem.service;

import dev.bedesi.sms.schoolmanagementsystem.mysql.entity.CourseEntity;
import dev.bedesi.sms.schoolmanagementsystem.mysql.entity.StudentCourseEntity;
import dev.bedesi.sms.schoolmanagementsystem.mysql.entity.StudentEntity;
import dev.bedesi.sms.schoolmanagementsystem.mysql.entity.TeacherEntity;
import dev.bedesi.sms.schoolmanagementsystem.mysql.repository.CourseRepository;
import dev.bedesi.sms.schoolmanagementsystem.mysql.repository.StudentRepository;
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

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentCourseService studentCourseService;

    public List<CourseEntity> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<CourseEntity> getCourseById(int id) {
        return courseRepository.findById(id);
    }

    public CourseEntity createCourse(CourseEntity course) {
        return courseRepository.save(course);
    }

    public CourseEntity assignTeacher(CourseEntity courseEntity) {
        Objects.requireNonNull(courseEntity, "Course cannot be null");
        CourseEntity existingCourse = courseRepository.findById(courseEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Course with ID " + courseEntity.getId() + " not found or already inactive"));

        TeacherEntity teacher = teacherRepository.findById(courseEntity.getTeacher().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Teacher with ID " + courseEntity.getTeacher().getId() + " not found or already inactive"));

        existingCourse.setTeacher(teacher);
        return courseRepository.save(existingCourse);
    }

    public StudentCourseEntity assignStudent(StudentCourseEntity studentCourseEntity) {
        int courseID=studentCourseEntity.getCourse().getId();
        int stdID=studentCourseEntity.getStudent().getId();
        CourseEntity existingCourse = courseRepository.findById(courseID)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Course with ID " + courseID + " not found or already inactive"));
        StudentEntity existingStudent =studentService.getStudentById(stdID)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Teacher with ID " +stdID+ " not found or already inactive"));
        studentCourseEntity.setStudent(existingStudent);
        studentCourseEntity.setCourse(existingCourse);
       return studentCourseService.enrollStudent(studentCourseEntity);
    }
}