package com.vmnog.certification_nlw.modules.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionAlternativeCorrectDTO {
    private UUID id;
    private String technology;
    private String description;

    private List<AlternativesDTO> alternatives;
}
