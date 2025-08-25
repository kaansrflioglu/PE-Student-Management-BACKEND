package com.kaansrflioglu.pe.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SportsRequest {
    @NotBlank(message = "Name can not be empty")
    private String name;
}