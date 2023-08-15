package com.khureturn.community.domain.notice;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NoticeComment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_comment_id")
    private Long id;

    private String noticeCommentContext;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="notice_id")
    private Notice notice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;

}
