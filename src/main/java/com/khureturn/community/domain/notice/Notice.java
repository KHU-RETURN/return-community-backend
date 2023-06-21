package com.khureturn.community.domain.notice;

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
public class Notice extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "notice_id")
    private Long id;
    @Column(nullable = false)
    private String noticeTitle;
    private String noticeContent;
    private boolean noticePinStatus;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int noticeLikeCount;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int noticeScrapCount;

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
