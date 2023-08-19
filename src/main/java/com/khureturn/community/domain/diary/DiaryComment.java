package com.khureturn.community.domain.diary;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DiaryComment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_comment_id")
    private Long id;
    private String diaryCommentContent;

    private String diaryHashtag;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int recommentCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private DiaryComment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<DiaryComment> children = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void update(String diaryCommentContent){

        this.diaryCommentContent = diaryCommentContent;
    }


}
