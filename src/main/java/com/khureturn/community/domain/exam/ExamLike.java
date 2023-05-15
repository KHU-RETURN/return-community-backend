package com.khureturn.community.domain.exam;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.exam.Exam;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ExamLike {
    @Id
    @GeneratedValue
    @Column(name ="exam_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="exam_id")
    private Exam exam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;
}
