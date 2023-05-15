package com.khureturn.community.domain.diary;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class DiaryScrap {

    @Id @GeneratedValue
    @Column(name ="diary_scrap_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
