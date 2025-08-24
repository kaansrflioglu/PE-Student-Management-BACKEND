package com.kaansrflioglu.pe.repository;

import com.kaansrflioglu.pe.model.Parent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends MongoRepository<Parent, String> {
}