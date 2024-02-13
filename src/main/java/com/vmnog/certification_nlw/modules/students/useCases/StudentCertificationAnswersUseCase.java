package com.vmnog.certification_nlw.modules.students.useCases;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.vmnog.certification_nlw.modules.questions.entities.QuestionEntity;
import com.vmnog.certification_nlw.modules.questions.repositories.QuestionRepository;
import com.vmnog.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import com.vmnog.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import com.vmnog.certification_nlw.modules.students.entities.AnswerCertificationEntity;
import com.vmnog.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.vmnog.certification_nlw.modules.students.entities.StudentEntity;
import com.vmnog.certification_nlw.modules.students.repositories.CertificationStudentRepository;
import com.vmnog.certification_nlw.modules.students.repositories.StudentRepository;

@Service
public class StudentCertificationAnswersUseCase {
    @Autowired
    private QuestionRepository questionRepository;

    // TODO: check why studentEntity is returning null
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CertificationStudentRepository certificationStudentRepository;

    @Autowired
    private VerifyHasCertificationUseCase verifyHasCertificationUseCase;

    private int correctAnswersCount = 0;

    public CertificationStudentEntity execute(StudentCertificationAnswerDTO dto) throws Exception {
        var hasCertification = this.verifyHasCertificationUseCase
                .execute(new VerifyHasCertificationDTO(dto.getEmail(), dto.getTechnology()));

        if (hasCertification) {
            throw new Exception("Student already has certification for this technology.");
        }

        List<QuestionEntity> questions = this.questionRepository.findByTechnology(dto.getTechnology());
        List<AnswerCertificationEntity> answersCertification = new ArrayList<>();

        dto.getQuestions().stream().forEach(question -> {
            var questionEntity = questions.stream()
                    .filter(q -> q.getId().equals(question.getQuestionID()))
                    .findFirst()
                    .get();

            var correctAlternatives = questionEntity.getAlternatives()
                    .stream()
                    .filter(alternative -> alternative.isCorrect())
                    .findFirst()
                    .get();

            if (correctAlternatives.getId().equals(question.getAlternativeID())) {
                question.setCorrect(true);
                correctAnswersCount++;
            } else {
                question.setCorrect(false);
            }

            UUID answerID = UUID.randomUUID();
            var answerCertification = AnswerCertificationEntity.builder()
                    .id(answerID)
                    .questionID(question.getQuestionID())
                    .answerID(question.getAlternativeID())
                    .isCorrect(question.isCorrect())
                    .build();
            answersCertification.add(answerCertification);
        });

        var student = studentRepository.findByEmail(dto.getEmail());
        UUID studentID;

        if (student.isEmpty()) {
            var studentCreated = StudentEntity.builder().email(dto.getEmail()).build();
            studentRepository.save(studentCreated);
            studentID = studentCreated.getId();
        } else {
            studentID = student.get().getId();
        }

        CertificationStudentEntity certificationStudentEntity = CertificationStudentEntity.builder()
                .studentId(studentID)
                .technology(dto.getTechnology())
                .grade(correctAnswersCount)
                .build();

        var certificationStudentCreated = certificationStudentRepository.save(certificationStudentEntity);
        answersCertification.stream().forEach(answer -> {
            answer.setStudentID(studentID);
            answer.setCertificationID(certificationStudentCreated.getId());
            answer.setCertificationStudentEntity(certificationStudentEntity);
            answer.setCreatedAt(LocalDateTime.now());
        });

        certificationStudentEntity.setAnswerCertificationEntities(answersCertification);

        return certificationStudentCreated;
    }
}
