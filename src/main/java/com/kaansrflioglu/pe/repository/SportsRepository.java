package com.kaansrflioglu.pe.repository;

import com.kaansrflioglu.pe.model.Sports;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportsRepository extends MongoRepository<Sports, String> {
    boolean existsByName(String name);
}

