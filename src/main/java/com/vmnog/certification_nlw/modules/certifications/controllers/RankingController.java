package com.vmnog.certification_nlw.modules.certifications.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vmnog.certification_nlw.modules.certifications.useCases.RankingGetTop10UseCase;
import com.vmnog.certification_nlw.modules.students.entities.CertificationStudentEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/ranking")
public class RankingController {
    @Autowired
    private RankingGetTop10UseCase rankingGetTop10UseCase;

    @GetMapping("/top10")
    public ResponseEntity<Object> getTop10() {
        try {
            var result = rankingGetTop10UseCase.execute();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
