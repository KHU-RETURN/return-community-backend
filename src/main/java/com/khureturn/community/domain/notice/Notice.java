package com.khureturn.community.domain.notice;

import com.khureturn.community.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Notice {
    @Id @GeneratedValue
    @Column(name = "notice_id")
    private Long id;
    private String notice_title;
    private String notice_content;
    private boolean notice_pin_status;
    private LocalDateTime notice_create_date;
    private int notice_like_count;
    private int notice_scrap_count;

    @OneToMany(mappedBy ="notice")
    private List<NoticeFile> noticeFiles = new ArrayList<>();

    @OneToMany(mappedBy ="notice")
    private List<NoticeLike> noticeLikes = new ArrayList<>();

    @OneToMany(mappedBy ="notice")
    private List<NoticeScrap> noticeScraps = new ArrayList<>();

    @OneToMany(mappedBy ="notice")
    private List<NoticeComment> noticeComments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;
}
