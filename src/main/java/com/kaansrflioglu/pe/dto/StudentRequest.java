package com.kaansrflioglu.pe.dto;

import com.kaansrflioglu.pe.model.Parent;
import com.kaansrflioglu.pe.model.Sports;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentRequest {

    @NotBlank(message = "İsim boş olamaz")
    private String name;

    @NotBlank(message = "Soyisim boş olamaz")
    private String surname;

    private double weight;
    private double height;
    private double pace;
    private String flexibility;
    private double leap;
    private double armStrength;
    private double legStrength;
    private String muscleAnatomy;
    private Byte gradeLevel;
    private String gradeSection;

    private String picture;

    private List<Sports> preferredSports;
    private List<Sports> suitableSports;

    private List<Parent> parents;
}