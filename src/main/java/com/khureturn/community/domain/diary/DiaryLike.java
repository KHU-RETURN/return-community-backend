package com.khureturn.community.domain.diary;

import com.khureturn.community.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DiaryLike {
    @Id @GeneratedValue
    @Column(name ="diary_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="diary_id")
    private Diary diaryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member memberId;

}
