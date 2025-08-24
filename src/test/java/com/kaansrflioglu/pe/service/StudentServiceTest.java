package com.kaansrflioglu.pe.service;

import com.kaansrflioglu.pe.exception.ResourceNotFoundException;
import com.kaansrflioglu.pe.model.Student;
import com.kaansrflioglu.pe.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    // Repository mock’lanıyor, gerçek DB kullanılmıyor
    private final StudentRepository repository = mock(StudentRepository.class);
    private final StudentService service = new StudentService(repository);

    @Test
    void testGetAll() {
        // Hazırlık: Repository’den iki öğrenci dönmesi bekleniyor
        Student s1 = new Student("1","Ali","ali@example.com",20);
        Student s2 = new Student("2","Ayşe","ayse@example.com",22);
        when(repository.findAll()).thenReturn(List.of(s1,s2));

        // Test: getAll() metodu tüm öğrencileri döndürmeli
        List<Student> students = service.getAll();
        assertEquals(2, students.size());

        // Doğrulama: repository.findAll() bir kez çağrıldı mı?
        verify(repository,times(1)).findAll();
    }

    @Test
    void testGetByIdFound() {
        // Hazırlık: Öğrenci mevcut
        Student student = new Student("1","Ali","ali@example.com",20);
        when(repository.findById("1")).thenReturn(Optional.of(student));

        // Test: getById() doğru öğrenci dönmeli
        Student result = service.getById("1");
        assertEquals("Ali", result.getName());
    }

    @Test
    void testGetByIdNotFound() {
        // Hazırlık: Öğrenci bulunmuyor
        when(repository.findById("2")).thenReturn(Optional.empty());

        // Test: getById() ResourceNotFoundException fırlatmalı
        assertThrows(ResourceNotFoundException.class, () -> service.getById("2"));
    }

    @Test
    void testCreateStudent() {
        // Hazırlık: Yeni öğrenci oluşturulacak
        Student student = new Student(null,"Ali","ali@example.com",20);
        Student saved = new Student("1","Ali","ali@example.com",20);
        when(repository.save(student)).thenReturn(saved);

        // Test: create() metodu ID atanmış öğrenci döndürmeli
        Student result = service.create(student);
        assertEquals("1", result.getId());
    }

    @Test
    void testUpdateStudent() {
        // Hazırlık: Mevcut öğrenci ve güncellenecek veri
        Student existing = new Student("1","Ali","ali@example.com",20);
        Student updateData = new Student(null,"Ali Updated","ali2@example.com",21);
        when(repository.findById("1")).thenReturn(Optional.of(existing));
        when(repository.save(any(Student.class))).thenReturn(updateData);

        // Test: update() metodu verileri güncellemeli
        Student result = service.update("1", updateData);
        assertEquals("Ali Updated", result.getName());
    }

    @Test
    void testDeleteStudent() {
        // Test: delete() metodu repository.deleteById() çağrıyor mu?
        doNothing().when(repository).deleteById("1");
        service.delete("1");
        verify(repository,times(1)).deleteById("1");
    }
}