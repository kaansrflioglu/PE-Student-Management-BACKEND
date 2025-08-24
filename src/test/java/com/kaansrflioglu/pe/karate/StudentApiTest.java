package com.kaansrflioglu.pe.karate;

import com.intuit.karate.junit5.Karate;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Testcontainers
@ActiveProfiles("test")
public class StudentApiTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0")
            .withExposedPorts(27017);

    @BeforeAll
    static void configureProperties() {
        mongoDBContainer.start();
        System.setProperty("spring.data.mongodb.uri", mongoDBContainer.getReplicaSetUrl("test_db"));
    }

    @Karate.Test
    Karate testStudents() {
        return Karate.run("classpath:karate/students.feature");
    }
}