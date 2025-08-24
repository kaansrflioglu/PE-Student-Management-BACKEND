package com.kaansrflioglu.pe.dto;

import com.kaansrflioglu.pe.model.Sports;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParentRequest {

    @NotBlank(message = "İsim boş olamaz")
    private String name;

    @NotBlank(message = "Soyisim boş olamaz")
    private String surname;

    private String phone;
    private double height;
    private String relation;

    private List<Sports> sportsBackground;
}