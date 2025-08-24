package com.kaansrflioglu.pe.service;

import com.kaansrflioglu.pe.exception.ResourceNotFoundException;
import com.kaansrflioglu.pe.model.Parent;
import com.kaansrflioglu.pe.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParentService {

    private final ParentRepository repository;

    public List<Parent> getAll() {
        return repository.findAll();
    }

    public Parent getById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent bulunamadı: " + id));
    }

    public Parent create(Parent parent) {
        return repository.save(parent);
    }

    public Parent update(String id, Parent parent) {
        Parent existing = getById(id);

        existing.setName(parent.getName());
        existing.setSurname(parent.getSurname());
        existing.setPhone(parent.getPhone());
        existing.setHeight(parent.getHeight());
        existing.setRelation(parent.getRelation());
        existing.setSportsBackground(parent.getSportsBackground());

        return repository.save(existing);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}