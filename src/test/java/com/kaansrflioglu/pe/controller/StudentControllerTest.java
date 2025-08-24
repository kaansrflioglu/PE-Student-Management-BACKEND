package com.kaansrflioglu.pe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaansrflioglu.pe.dto.StudentRequest;
import com.kaansrflioglu.pe.model.Student;
import com.kaansrflioglu.pe.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class) // Mockito extension ile mockları yönet
class StudentControllerTest {

    @Mock
    private StudentService service; // Service bağımlılığı mock

    @InjectMocks
    private StudentController controller; // Controller’a mockları inject et

    private MockMvc mockMvc; // HTTP isteklerini simüle eder

    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON dönüştürücü

    @BeforeEach
    void setup() {
        // Standalone MockMvc setup
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetById() throws Exception {
        // Hazırlık: Mock service bir öğrenci döndürüyor
        Student student = new Student("1","Ali","ali@example.com",20);
        Mockito.when(service.getById("1")).thenReturn(student);

        // Test: GET /students/1 endpoint’i doğru JSON dönüyor mu?
        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ali"));
    }

    @Test
    void testCreateStudent() throws Exception {
        // Hazırlık: DTO request ve mock service cevabı
        StudentRequest request = new StudentRequest("Ali","ali@example.com",20);
        Student saved = new Student("1","Ali","ali@example.com",20);
        Mockito.when(service.create(any(Student.class))).thenReturn(saved);

        // Test: POST /students endpoint’i doğru JSON ve status dönüyor mu?
        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Ali"));
    }

    @Test
    void testDeleteStudent() throws Exception {
        // Hazırlık: delete() metodu mock
        Mockito.doNothing().when(service).delete("1");

        // Test: DELETE /students/1 endpoint’i 204 döndürüyor mu?
        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isNoContent());
    }
}