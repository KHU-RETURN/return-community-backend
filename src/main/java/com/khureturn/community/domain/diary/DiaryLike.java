package com.khureturn.community.domain.diary;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.exam.Exam;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DiaryLike {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="diary_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="diary_id")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;

}
