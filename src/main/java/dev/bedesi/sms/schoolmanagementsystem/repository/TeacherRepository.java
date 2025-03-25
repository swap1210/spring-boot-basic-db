package dev.bedesi.sms.schoolmanagementsystem.repository;

import dev.bedesi.sms.schoolmanagementsystem.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}