package com.vmnog.certification_nlw.modules.students.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vmnog.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import com.vmnog.certification_nlw.modules.students.repositories.CertificationStudentRepository;

@Service
public class VerifyHasCertificationUseCase {
    @Autowired
    private CertificationStudentRepository certificationStudentRepository;

    public boolean execute(VerifyHasCertificationDTO dto) {
        var student = this.certificationStudentRepository.findByStudentEmailAndTechnology(
                dto.getEmail(),
                dto.getTechnology());

        return !student.isEmpty();
    }
}