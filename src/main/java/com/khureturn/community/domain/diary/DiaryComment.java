package com.khureturn.community.domain.diary;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.diary.Diary;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class DiaryComment {

    @Id @GeneratedValue
    @Column(name = "diary_comment_id")
    private Long id;
    private String comment_content;
    private LocalDateTime comment_create_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="diary_id")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public DiaryComment(String comment_content){
        this.comment_content = comment_content;
    }

    public void update(String comment_content){
        this.comment_content = comment_content;
    }
}
