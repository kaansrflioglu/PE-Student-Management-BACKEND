package com.kaansrflioglu.pe.repository;

import com.kaansrflioglu.pe.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    List<Student> findByParentsId(String parentId);
    @Query(value = "{}", fields = "{ '_id' : 1, 'name' : 1, 'surname' : 1, 'gradeLevel' : 1, 'gradeSection' : 1, 'picture' : 1 }")
    List<Student> findAllSummary();
}
