package com.khureturn.community.domain;

import com.khureturn.community.domain.base.BaseEntity;
import com.khureturn.community.domain.common.ManagerStatus;
import com.khureturn.community.domain.common.RoleStatus;
import com.khureturn.community.domain.diary.Diary;
import com.khureturn.community.domain.diary.DiaryComment;
import com.khureturn.community.domain.diary.DiaryLike;
import com.khureturn.community.domain.diary.DiaryScrap;
import com.khureturn.community.domain.exam.Exam;
import com.khureturn.community.domain.exam.ExamLike;
import com.khureturn.community.domain.exam.ExamScrap;
import com.khureturn.community.domain.notice.Notice;
import com.khureturn.community.domain.notice.NoticeLike;
import com.khureturn.community.domain.notice.NoticeScrap;
import jakarta.persistence.*;
import lombok.*;


import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long memberId;

    @Column(unique = true, nullable = false)
    private String studentId;


    @Column(unique = true, nullable = false)
    private String googleSub;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phoneNumber;
    private String profileImg;

    @Enumerated(EnumType.STRING)
    private ManagerStatus managerStatus;

    @Enumerated(EnumType.STRING)
    private RoleStatus roleStatus;

    private boolean isPaid;

    @OneToMany(mappedBy ="member")
    private List<Diary> diarys = new ArrayList<>();

    @OneToMany(mappedBy ="member")
    private List<DiaryScrap> diaryScraps = new ArrayList<>();

    @OneToMany(mappedBy ="member")
    private List<DiaryLike> diaryLikes = new ArrayList<>();

    @OneToMany(mappedBy ="member")
    private List<DiaryComment> diaryComments = new ArrayList<>();

    @OneToMany(mappedBy ="member")
    private List<Exam> exams = new ArrayList<>();

    @OneToMany(mappedBy ="member")
    private List<ExamScrap> examScraps = new ArrayList<>();

    @OneToMany(mappedBy ="member")
    private List<ExamLike> examLikes = new ArrayList<>();

    @OneToMany(mappedBy ="member")
    private List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy ="member")
    private List<NoticeLike> noticeLikes = new ArrayList<>();

    @OneToMany(mappedBy ="member")
    private List<NoticeScrap> noticeScraps = new ArrayList<>();




}
