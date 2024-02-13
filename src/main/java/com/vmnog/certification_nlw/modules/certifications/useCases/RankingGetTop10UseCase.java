package com.vmnog.certification_nlw.modules.certifications.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.vmnog.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.vmnog.certification_nlw.modules.students.repositories.CertificationStudentRepository;

@Service
public class RankingGetTop10UseCase {
    @Autowired
    private CertificationStudentRepository certificationStudentRepository;

    public List<CertificationStudentEntity> execute() {
        var result = this.certificationStudentRepository.findTop10ByTechnologyOrderByScoreDesc();
        return result;
    }
}
