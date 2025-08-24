package com.kaansrflioglu.pe.repository;

import com.kaansrflioglu.pe.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    List<Student> findByParentsId(String parentId);
}
