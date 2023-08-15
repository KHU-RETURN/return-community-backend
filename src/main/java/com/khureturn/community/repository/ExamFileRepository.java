package com.khureturn.community.repository;

import com.khureturn.community.domain.exam.Exam;
import com.khureturn.community.domain.exam.ExamFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamFileRepository extends JpaRepository<ExamFile, Long> {

    List<ExamFile> findAllByExam(Exam exam);
    Optional<ExamFile> findByIdAndExam(Long examFileId, Exam exam);
}
