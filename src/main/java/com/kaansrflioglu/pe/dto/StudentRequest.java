package com.kaansrflioglu.pe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentRequest {
    @NotBlank(message = "İsim boş olamaz")
    private String name;

    @Email(message = "Geçerli bir e-posta giriniz")
    private String email;

    private int age;
}
