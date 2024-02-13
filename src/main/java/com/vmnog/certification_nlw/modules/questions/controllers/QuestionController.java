package com.vmnog.certification_nlw.modules.questions.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.vmnog.certification_nlw.modules.questions.dto.AlternativesDTO;
import com.vmnog.certification_nlw.modules.questions.dto.QuestionAlternativeCorrectDTO;
import com.vmnog.certification_nlw.modules.questions.entities.AlternativesEntity;
import com.vmnog.certification_nlw.modules.questions.entities.QuestionEntity;
import com.vmnog.certification_nlw.modules.questions.repositories.QuestionRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/technology/{technology}")
    public List<QuestionAlternativeCorrectDTO> findByTechnology(@PathVariable String technology) {
        var questions = this.questionRepository.findByTechnology(technology);

        var mappedQuestions = questions.stream().map(question -> mapQuestionToDTO(question))
                .collect(Collectors.toList());

        return mappedQuestions;
    }

    static QuestionAlternativeCorrectDTO mapQuestionToDTO(QuestionEntity question) {
        var questionDTO = QuestionAlternativeCorrectDTO.builder()
                .id(question.getId())
                .technology(question.getTechnology())
                .description(question.getDescription()).build();

        List<AlternativesDTO> alternativesDTO = question.getAlternatives().stream()
                .map(alternative -> mapAlternativesDTO(alternative)).collect(Collectors.toList());

        questionDTO.setAlternatives(alternativesDTO);

        return questionDTO;
    }

    static AlternativesDTO mapAlternativesDTO(AlternativesEntity alternative) {
        return AlternativesDTO.builder()
                .id(alternative.getId())
                .description(alternative.getDescription()).build();
    }
}
