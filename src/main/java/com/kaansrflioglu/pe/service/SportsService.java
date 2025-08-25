package com.kaansrflioglu.pe.service;

import com.kaansrflioglu.pe.exception.ResourceNotFoundException;
import com.kaansrflioglu.pe.model.Sports;
import com.kaansrflioglu.pe.repository.SportsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SportsService {

    private final SportsRepository repository;

    public List<Sports> getAll() {
        log.info("Tüm sporlar listeleniyor");
        return repository.findAll();
    }

    public Sports getById(String id) {
        log.info("Spor getiriliyor: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Spor bulunamadı: " + id));
    }

    public Sports create(Sports sports) {
        log.info("Yeni spor oluşturuluyor: {}", sports.getName());
        return repository.save(sports);
    }

    public Sports update(String id, Sports sports) {
        Sports existing = getById(id);
        existing.setName(sports.getName());
        log.info("Spor güncelleniyor: {}", id);
        return repository.save(existing);
    }

    public void delete(String id) {
        log.info("Spor siliniyor: {}", id);
        repository.deleteById(id);
    }
}