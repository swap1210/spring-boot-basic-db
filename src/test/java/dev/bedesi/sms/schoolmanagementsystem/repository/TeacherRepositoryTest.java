package dev.bedesi.sms.schoolmanagementsystem.repository;

import dev.bedesi.sms.schoolmanagementsystem.mysql.entity.Teacher;
import dev.bedesi.sms.schoolmanagementsystem.mysql.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    void findAll_ReturnsAllTeachers() {
        // Given
        Teacher teacher1 = new Teacher("John Doe");
        Teacher teacher2 = new Teacher("Jane Smith");
        teacherRepository.save(teacher1);
        teacherRepository.save(teacher2);

        // When
        List<Teacher> teachers = teacherRepository.findAll();

        // Then
        assertThat(teachers).hasSize(2);
        assertThat(teachers).extracting(Teacher::getName)
                .containsExactlyInAnyOrder("John Doe", "Jane Smith");
    }

    @Test
    void findById_ExistingId_ReturnsTeacher() {
        // Given
        Teacher teacher = new Teacher("John Doe");
        Teacher savedTeacher = teacherRepository.save(teacher);

        // When
        Optional<Teacher> foundTeacher = teacherRepository.findById(savedTeacher.getId());

        // Then
        assertThat(foundTeacher).isPresent();
        assertThat(foundTeacher.get().getName()).isEqualTo("John Doe");
    }

    @Test
    void findById_NonExistingId_ReturnsEmpty() {
        // When
        Optional<Teacher> foundTeacher = teacherRepository.findById(999L);

        // Then
        assertThat(foundTeacher).isNotPresent();
    }

    @Test
    void save_NewTeacher_ReturnsSavedTeacherWithId() {
        // Given
        Teacher teacher = new Teacher("John Doe");

        // When
        Teacher savedTeacher = teacherRepository.save(teacher);

        // Then
        assertThat(savedTeacher.getId()).isNotNull();
        assertThat(savedTeacher.getName()).isEqualTo("John Doe");
    }

    @Test
    @Sql(statements = "INSERT INTO teacher (id, name) VALUES (1, 'Test Teacher')")
    void findAll_WithPreloadedData_ReturnsCorrectData() {
        // When
        List<Teacher> teachers = teacherRepository.findAll();

        // Then
        assertThat(teachers).hasSize(1);
        assertThat(teachers.get(0).getId()).isEqualTo(1L);
        assertThat(teachers.get(0).getName()).isEqualTo("Test Teacher");
    }
}