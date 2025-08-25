package com.kaansrflioglu.pe.controller;

import com.kaansrflioglu.pe.dto.StudentRequest;
import com.kaansrflioglu.pe.dto.StudentResponse;
import com.kaansrflioglu.pe.model.Parent;
import com.kaansrflioglu.pe.model.Sports;
import com.kaansrflioglu.pe.model.Student;
import com.kaansrflioglu.pe.repository.ParentRepository;
import com.kaansrflioglu.pe.repository.SportsRepository;
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
    private final SportsRepository sportsRepository;
    private final ParentRepository parentRepository;

    public StudentController(StudentService service,
                             SportsRepository sportsRepository,
                             ParentRepository parentRepository) {
        this.service = service;
        this.sportsRepository = sportsRepository;
        this.parentRepository = parentRepository;
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
                .surname(student.getSurname())
                .weight(student.getWeight())
                .height(student.getHeight())
                .pace(student.getPace())
                .flexibility(student.getFlexibility())
                .leap(student.getLeap())
                .picture(student.getPicture())
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

    private Student toEntity(StudentRequest request) {
        List<Sports> preferredSports = request.getPreferredSports() == null ? List.of() :
                request.getPreferredSports().stream()
                        .map(s -> sportsRepository.findById(s.getId())
                                .orElseThrow(() -> new RuntimeException("Sports not found: " + s.getId())))
                        .toList();

        List<Sports> suitableSports = request.getSuitableSports() == null ? List.of() :
                request.getSuitableSports().stream()
                        .map(s -> sportsRepository.findById(s.getId())
                                .orElseThrow(() -> new RuntimeException("Sports not found: " + s.getId())))
                        .toList();

        List<Parent> parents = request.getParents() == null ? List.of() :
                request.getParents().stream()
                        .map(p -> parentRepository.findById(p.getId())
                                .orElseThrow(() -> new RuntimeException("Parent not found: " + p.getId())))
                        .toList();

        return Student.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .weight(request.getWeight())
                .height(request.getHeight())
                .pace(request.getPace())
                .flexibility(request.getFlexibility())
                .leap(request.getLeap())
                .picture(request.getPicture())
                .armStrength(request.getArmStrength())
                .legStrength(request.getLegStrength())
                .muscleAnatomy(request.getMuscleAnatomy())
                .gradeLevel(request.getGradeLevel())
                .gradeSection(request.getGradeSection())
                .preferredSports(preferredSports)
                .suitableSports(suitableSports)
                .parents(parents)
                .build();
    }


}