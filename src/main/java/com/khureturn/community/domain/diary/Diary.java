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
public class Diary extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "diary_id")
    private Long id;

    @Column(nullable = false)
    private String diaryTitle;
    private String diaryContent;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Long diaryLikeCount;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Long diaryScrapCount;
    private Long diaryCommentCount;

    private Boolean isAnonymous;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<DiaryFile> diaryFiles = new ArrayList<>();

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<DiaryLike> diaryLikes = new ArrayList<>();

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<DiaryScrap> diaryScraps = new ArrayList<>();
    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<DiaryComment> diaryComments = new ArrayList<>();

    public void update(String diaryTitle, String diaryContent, Boolean isAnonymous){
        this.diaryTitle=diaryTitle;
        this.diaryContent=diaryContent;
        this.isAnonymous=isAnonymous;
    }

}
