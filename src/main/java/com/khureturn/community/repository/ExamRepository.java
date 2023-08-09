package com.khureturn.community.repository;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.exam.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExamRepository extends JpaRepository<Exam,Long> {

    Boolean existsByMember(Member member);

    @Query("select e from Exam e where e.examContent like %?1% or e.examTitle like %?1%")
    Page<Exam> findByExamContentContainingIgnoreCaseOrExamTitleContainingIgnoreCase(String search, Pageable pageable);

    Page<Exam> findAll(Pageable pageable);
}

