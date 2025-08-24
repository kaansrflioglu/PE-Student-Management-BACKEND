package com.kaansrflioglu.pe.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {
    private String id;
    private String name;
    private String email;
    private int age;
}
