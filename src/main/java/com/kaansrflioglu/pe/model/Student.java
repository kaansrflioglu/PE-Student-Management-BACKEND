package com.kaansrflioglu.pe.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Document(collection = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    private String id;

    @NotBlank(message = "Name can not be empty")
    private String name;

    @NotBlank(message = "Surname can not be empty")
    private String surname;

    private double weight;          // kilo
    private double height;          // boy
    private double pace;            // sürat
    private String flexibility;     // esneklik
    private double leap;            // sıçrama
    private double armStrength;     // kol kuvveti
    private double legStrength;     // bacak kuvveti
    private String muscleAnatomy;   // kas yapısı
    private Byte gradeLevel;        // sınıf seviyesi
    private String gradeSection;    // şube

    private List<Sports> preferredSports;  // öğrencinin tercih ettiği sporlar
    private List<Sports> suitableSports;   // öğrenciye uygun sporlar

    private List<Parent> parents;   // ebeveyn bilgileri
}
