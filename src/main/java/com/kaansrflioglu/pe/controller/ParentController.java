package com.kaansrflioglu.pe.controller;

import com.kaansrflioglu.pe.dto.ParentRequest;
import com.kaansrflioglu.pe.dto.ParentResponse;
import com.kaansrflioglu.pe.dto.StudentResponse;
import com.kaansrflioglu.pe.model.Parent;
import com.kaansrflioglu.pe.model.Sports;
import com.kaansrflioglu.pe.model.Student;
import com.kaansrflioglu.pe.repository.SportsRepository;
import com.kaansrflioglu.pe.repository.StudentRepository;
import com.kaansrflioglu.pe.service.ParentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/parents")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService service;
    private final StudentRepository studentRepository;
    private final SportsRepository sportsRepository;

    // --- Parent CRUD ---
    @GetMapping
    public List<ParentResponse> getAll() {
        return service.getAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ParentResponse getById(@PathVariable String id) {
        return toResponse(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<ParentResponse> create(@Valid @RequestBody ParentRequest request) {
        Parent parent = service.create(toEntity(request));
        return ResponseEntity.ok(toResponse(parent));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParentResponse> update(@PathVariable String id, @Valid @RequestBody ParentRequest request) {
        Parent updated = service.update(id, toEntity(request));
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- Parent -> Students endpoint ---
    @GetMapping("/{id}/students")
    public List<StudentResponse> getStudentsByParent(@PathVariable String id) {
        List<Student> students = studentRepository.findByParentsId(id);
        return students.stream()
                .map(this::studentToResponse) // ayrı mapping metodu
                .collect(Collectors.toList());
    }

    // --- Mapping: Parent -> ParentResponse ---
    private ParentResponse toResponse(Parent parent) {
        return ParentResponse.builder()
                .id(parent.getId())
                .name(parent.getName())
                .surname(parent.getSurname())
                .phone(parent.getPhone())
                .height(parent.getHeight())
                .relation(parent.getRelation())
                .sportsBackground(parent.getSportsBackground())
                .build();
    }

    // --- Mapping: Student -> StudentResponse ---
    private StudentResponse studentToResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .surname(student.getSurname())
                .weight(student.getWeight())
                .height(student.getHeight())
                .pace(student.getPace())
                .flexibility(student.getFlexibility())
                .leap(student.getLeap())
                .armStrength(student.getArmStrength())
                .legStrength(student.getLegStrength())
                .muscleAnatomy(student.getMuscleAnatomy())
                .gradeLevel(student.getGradeLevel())
                .gradeSection(student.getGradeSection())
                .preferredSports(student.getPreferredSports())
                .suitableSports(student.getSuitableSports())
                .parents(student.getParents())
                .build();
    }

    // --- Mapping: ParentRequest -> Parent ---
    private Parent toEntity(ParentRequest request) {
        List<Sports> sportsBackground = request.getSportsBackground() == null ? List.of() :
                request.getSportsBackground().stream()
                        .map(s -> sportsRepository.findById(s.getId())
                                .orElseThrow(() -> new RuntimeException("Sports not found: " + s.getId())))
                        .toList();

        return Parent.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .phone(request.getPhone())
                .height(request.getHeight())
                .relation(request.getRelation())
                .sportsBackground(sportsBackground)
                .build();
    }

}