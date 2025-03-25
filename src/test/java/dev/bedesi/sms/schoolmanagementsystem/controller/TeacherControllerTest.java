package dev.bedesi.sms.schoolmanagementsystem.controller;

import dev.bedesi.sms.schoolmanagementsystem.entity.Teacher;
import dev.bedesi.sms.schoolmanagementsystem.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TeacherController.class)
public class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TeacherService teacherService;

    private Teacher testTeacher;

    @BeforeEach
    void setUp() {
        testTeacher = new Teacher();
        testTeacher.setId(1L);
        testTeacher.setName("John Doe");
    }

    @Test
    void getAllTeachers_ReturnsTeacherList() throws Exception {
        List<Teacher> teachers = Arrays.asList(testTeacher);
        when(teacherService.getAllTeachers()).thenReturn(teachers);

        mockMvc.perform(get("/sms/teacher")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("John Doe")));
    }

    @Test
    void getTeacherById_ExistingId_ReturnsTeacher() throws Exception {
        when(teacherService.getTeacherById(1L)).thenReturn(Optional.of(testTeacher));

        mockMvc.perform(get("/sms/teacher/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John Doe")));
    }

    @Test
    void getTeacherById_NonExistingId_ReturnsNotFound() throws Exception {
        when(teacherService.getTeacherById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/sms/teacher/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createTeacher_ValidTeacher_ReturnsCreatedTeacher() throws Exception {
        when(teacherService.createTeacher(any(Teacher.class))).thenReturn(testTeacher);

        String teacherJson = "{\"name\":\"John Doe\"}";

        mockMvc.perform(post("/sms/teacher")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(teacherJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John Doe")));
    }
}