package com.kaansrflioglu.pe.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "sports")
public class Sports {

    @Id
    private String id;

    @NotBlank(message = "Name can not be empty")
    private String name;
}
