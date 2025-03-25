package dev.bedesi.sms.schoolmanagementsystem.repository;

import dev.bedesi.sms.schoolmanagementsystem.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}