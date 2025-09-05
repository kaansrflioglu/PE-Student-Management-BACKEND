package com.kaansrflioglu.pe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentSummaryResponse {
    private String id;
    private String name;
    private String surname;
    private Byte gradeLevel;
    private String gradeSection;
    private String picture;
}
