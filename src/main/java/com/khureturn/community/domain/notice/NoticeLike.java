package com.khureturn.community.domain.notice;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.notice.Notice;
import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Getter
public class NoticeLike {
    @Id
    @GeneratedValue
    @Column(name ="notice_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="notice_id")
    private Notice notice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;
}
