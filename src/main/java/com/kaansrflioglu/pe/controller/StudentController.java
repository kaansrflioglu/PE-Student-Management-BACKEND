package com.kaansrflioglu.pe.controller;

import com.kaansrflioglu.pe.dto.StudentRequest;
import com.kaansrflioglu.pe.dto.StudentResponse;
import com.kaansrflioglu.pe.model.Student;
import com.kaansrflioglu.pe.service.StudentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
@Slf4j
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public List<StudentResponse> getAll() {
        log.info("GET /students çağrıldı");
        return service.getAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public StudentResponse getById(@PathVariable String id) {
        log.info("GET /students/{} çağrıldı", id);
        return toResponse(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentRequest request) {
        log.info("POST /students çağrıldı");
        Student student = service.create(toEntity(request));
        return ResponseEntity.ok(toResponse(student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> update(@PathVariable String id, @Valid @RequestBody StudentRequest request) {
        log.info("PUT /students/{} çağrıldı", id);
        Student updated = service.update(id, toEntity(request));
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("DELETE /students/{} çağrıldı", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- DTO Mapping ---
    private StudentResponse toResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .age(student.getAge())
                .build();
    }

    private Student toEntity(StudentRequest request) {
        return Student.builder()
                .name(request.getName())
                .email(request.getEmail())
                .age(request.getAge())
                .build();
    }
}