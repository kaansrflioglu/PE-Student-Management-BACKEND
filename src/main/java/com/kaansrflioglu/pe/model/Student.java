package com.kaansrflioglu.pe.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

@Document(collection = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @Id
    private String id;

    @NotBlank(message = "İsim boş olamaz")
    private String name;

    @Email(message = "Geçerli bir e-posta giriniz")
    private String email;

    private int age;
}
