package com.khureturn.community.repository;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.exam.Exam;
import com.khureturn.community.domain.exam.ExamLike;
import com.khureturn.community.domain.exam.ExamScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamScrapRepository extends JpaRepository<ExamScrap, Long> {

    Boolean existsByMemberAndExam(Member member, Exam exam);

    Optional<ExamScrap> findByMemberAndExam(Member member, Exam exam);
}
