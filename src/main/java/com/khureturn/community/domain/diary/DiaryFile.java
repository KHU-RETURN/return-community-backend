package com.khureturn.community.domain.diary;

import com.khureturn.community.domain.MediaType;
import com.khureturn.community.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class DiaryFile {

    @Id @GeneratedValue
    @Column(name = "diary_file_id")
    private Long id;
    private String fileName;
    private String originalUrl;
    private String thumbUrl;
    private Long fileSize;
    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;
}
