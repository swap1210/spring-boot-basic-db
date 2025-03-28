package dev.bedesi.sms.schoolmanagementsystem.mysql.repository;

import dev.bedesi.sms.schoolmanagementsystem.mysql.entity.CourseEntity;
import dev.bedesi.sms.schoolmanagementsystem.mysql.entity.StudentCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface StudentCourseRepository extends JpaRepository<StudentCourseEntity, Integer> {
    @Query("SELECT c FROM Std_Course c WHERE c.id = :id AND c.active = true")
    Optional<StudentCourseEntity> findById(@Param("id") int id);
}