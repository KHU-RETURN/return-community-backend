package com.khureturn.community.repository;

import com.khureturn.community.domain.exam.ExamFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamFileRepository extends JpaRepository<ExamFile, Long> {
}
