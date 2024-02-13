package com.vmnog.certification_nlw.modules.students.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.vmnog.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import com.vmnog.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import com.vmnog.certification_nlw.modules.students.useCases.StudentCertificationAnswersUseCase;
import com.vmnog.certification_nlw.modules.students.useCases.VerifyHasCertificationUseCase;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private VerifyHasCertificationUseCase verifyHasCertificationUseCase;

    @Autowired
    private StudentCertificationAnswersUseCase studentCertificationAnswersUseCase;

    @PostMapping("/verifyHasCertification")
    public String verifyHasCertification(@RequestBody VerifyHasCertificationDTO verifyHasCertificationDTO) {
        var result = verifyHasCertificationUseCase.execute(verifyHasCertificationDTO);
        if (result) {
            return "Usuario pode fazer a prova";
        } else {
            return "Usuario não pode fazer a prova";
        }
    }

    @PostMapping("/certification/answer")
    public ResponseEntity<Object> certificationAnswer(
            @RequestBody StudentCertificationAnswerDTO studentCertificationAnswerDTO) throws Exception {
        try {
            var result = studentCertificationAnswersUseCase.execute(studentCertificationAnswerDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
