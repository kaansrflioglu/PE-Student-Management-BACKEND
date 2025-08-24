package com.kaansrflioglu.pe.service;

import com.kaansrflioglu.pe.exception.ResourceNotFoundException;
import com.kaansrflioglu.pe.model.Student;
import com.kaansrflioglu.pe.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAll() {
        log.info("Tüm öğrenciler listeleniyor");
        return repository.findAll();
    }

    public Student getById(String id) {
        log.info("Öğrenci getiriliyor: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Öğrenci bulunamadı: " + id));
    }

    public Student create(Student student) {
        log.info("Yeni öğrenci oluşturuluyor: {}", student.getName());
        return repository.save(student);
    }

    public Student update(String id, Student student) {
        Student existing = getById(id);
        existing.setName(student.getName());
        existing.setEmail(student.getEmail());
        existing.setAge(student.getAge());
        log.info("Öğrenci güncelleniyor: {}", id);
        return repository.save(existing);
    }

    public void delete(String id) {
        log.info("Öğrenci siliniyor: {}", id);
        repository.deleteById(id);
    }
}