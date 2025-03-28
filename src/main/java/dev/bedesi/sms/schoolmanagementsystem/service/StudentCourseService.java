package dev.bedesi.sms.schoolmanagementsystem.service;

import dev.bedesi.sms.schoolmanagementsystem.mysql.entity.CourseEntity;
import dev.bedesi.sms.schoolmanagementsystem.mysql.entity.StudentCourseEntity;
import dev.bedesi.sms.schoolmanagementsystem.mysql.entity.StudentEntity;
import dev.bedesi.sms.schoolmanagementsystem.mysql.repository.StudentCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentCourseService {
    @Autowired
    StudentCourseRepository studentCourseRepository;

    public StudentCourseEntity enrollStudent(StudentCourseEntity studentCourseEntity) {
        return studentCourseRepository.save(studentCourseEntity);
    }
    public boolean checkEnrollmentActive(StudentCourseEntity studentCourseEntity) {
        int courseID=studentCourseEntity.getCourse().getId();
        int stdID=studentCourseEntity.getStudent().getId();
        Optional<StudentCourseEntity> stdCourseOpt= studentCourseRepository.findByStudentIdAndCourseId(stdID,courseID);
        return stdCourseOpt.isPresent();
    }
}
