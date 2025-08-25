package com.kaansrflioglu.pe.controller;

import com.kaansrflioglu.pe.dto.SportsRequest;
import com.kaansrflioglu.pe.dto.SportsResponse;
import com.kaansrflioglu.pe.model.Sports;
import com.kaansrflioglu.pe.service.SportsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sports")
@Slf4j
@RequiredArgsConstructor
public class SportsController {

    private final SportsService service;

    @GetMapping
    public List<SportsResponse> getAll() {
        log.info("GET /sports çağrıldı");
        return service.getAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SportsResponse getById(@PathVariable String id) {
        log.info("GET /sports/{} çağrıldı", id);
        return toResponse(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<SportsResponse> create(@Valid @RequestBody SportsRequest request) {
        log.info("POST /sports çağrıldı");
        Sports sports = service.create(toEntity(request));
        return ResponseEntity.ok(toResponse(sports));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SportsResponse> update(@PathVariable String id, @Valid @RequestBody SportsRequest request) {
        log.info("PUT /sports/{} çağrıldı", id);
        Sports updated = service.update(id, toEntity(request));
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("DELETE /sports/{} çağrıldı", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- DTO Mapping ---
    private SportsResponse toResponse(Sports sports) {
        return SportsResponse.builder()
                .id(sports.getId())
                .name(sports.getName())
                .build();
    }

    private Sports toEntity(SportsRequest request) {
        return Sports.builder()
                .name(request.getName())
                .build();
    }
}