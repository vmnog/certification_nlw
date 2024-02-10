package com.vmnog.certification_nlw.modules.students.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "answers_certifications")
public class AnswerCertificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "certification_id")
    private UUID certificationID;

    @ManyToOne()
    @JoinColumn(name = "certification_id", insertable = false, updatable = false)
    private CertificationStudentEntity certificationStudentEntity;

    @Column(name = "student_id")
    private UUID studentID;

    @ManyToOne()
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private StudentEntity studentEntity;

    @Column(name = "question_id")
    private UUID questionID;

    // @ManyToOne()
    // @JoinColumn(name = "question_id", insertable = false, updatable = false)
    // private QuestionCertificationEntity questionCertificationEntity;

    @Column(name = "answer_id")
    private UUID answerID;

    // @ManyToOne()
    // @JoinColumn(name = "answer_id", insertable = false, updatable = false)
    // private AnswerEntity answerEntity;

    @Column(name = "is_correct")
    private boolean isCorrect;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
