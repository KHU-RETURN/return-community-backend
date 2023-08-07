package com.khureturn.community.repository;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.exam.Exam;
import com.khureturn.community.domain.exam.ExamLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamLikeRepository extends JpaRepository<ExamLike, Long> {

    Boolean existsByMemberAndExam(Member member, Exam exam);
}
