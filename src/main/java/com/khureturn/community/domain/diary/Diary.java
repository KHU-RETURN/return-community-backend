package com.khureturn.community.domain.diary;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Diary extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "diary_id")
    private Long id;

    @Column(nullable = false)
    private String diaryTitle;
    private String diaryContent;

    private Boolean isAnonymous;

    private int thumbnailIndex;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int diaryLikeCount;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int diaryScrapCount;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int diaryViewCount;
    @Column(columnDefinition = "INT DEFAULT 0")
    private int diaryCommentCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<DiaryFile> diaryFiles = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<DiaryLike> diaryLikes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<DiaryScrap> diaryScraps = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<DiaryComment> diaryComments = new ArrayList<>();

    public void update(String diaryTitle, String diaryContent, Boolean isAnonymous){
        this.diaryTitle=diaryTitle;
        this.diaryContent=diaryContent;
        this.isAnonymous=isAnonymous;
    }

    public void increaseHit(){this.diaryViewCount++;}
    public void increaseLike(){ this.diaryLikeCount++;}
    public void increaseScrap(){this.diaryScrapCount++;}
    public void decreaseLike(){ this.diaryLikeCount--;}
    public void decreaseScrap(){this.diaryScrapCount--;}



}
