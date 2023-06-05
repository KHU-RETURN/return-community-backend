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
public class NoticeFile extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "notice_file_id")
    private Long id;

    private String noticeFileName;
    private String noticeFileUrl;
    private String noticeFileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="notice_id")
    private Notice notice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;

}
