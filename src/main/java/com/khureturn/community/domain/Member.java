package com.khureturn.community.domain;



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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String studentId;
    private String name;
    private String email;
    private String phoneNumber;
    private String profileImg;

    @Enumerated(EnumType.STRING)
    private ManagerStatus managerStatus;

    @Enumerated(EnumType.STRING)
    private RoleStatus roleStatus;
    private boolean isPaid;
    private LocalDateTime memberCreateDate;

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

    @Builder
    public Member(String studentId, String name, String email, String phoneNumber, String profileImg){
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profileImg = profileImg;
    }

}
