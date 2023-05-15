package com.khureturn.community.domain.notice;

import com.khureturn.community.domain.Member;
import com.khureturn.community.domain.notice.Notice;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class NoticeFile {

    @Id
    @GeneratedValue
    @Column(name = "notice_file_id")
    private Long id;

    private String notice_file_name;
    private String notice_file_url;
    private String notice_file_size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="notice_id")
    private Notice notice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;

}
