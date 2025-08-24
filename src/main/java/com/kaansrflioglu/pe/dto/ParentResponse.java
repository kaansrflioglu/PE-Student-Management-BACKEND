package com.kaansrflioglu.pe.dto;

import com.kaansrflioglu.pe.model.Sports;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParentResponse {
    private String id;
    private String name;
    private String surname;
    private String phone;
    private double height;
    private String relation;
    private List<Sports> sportsBackground;
}