package com.khureturn.community.domain.diary;

import com.khureturn.community.domain.base.BaseEntity;
import com.khureturn.community.domain.common.MediaType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DiaryFile extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "diary_file_id")
    private Long id;
    private String diaryFileName;
    private String diaryOriginalUrl;
    private Integer diaryThumb;
    private String diaryFileSize;
    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

}
