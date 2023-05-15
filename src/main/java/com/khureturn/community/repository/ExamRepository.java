package com.khureturn.community.repository;

import com.khureturn.community.domain.exam.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam,Long> {
}
