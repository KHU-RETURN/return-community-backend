package com.khureturn.community.domain.diary;

import com.khureturn.community.domain.Member;
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
public class Diary {

    @Id @GeneratedValue
    @Column(name = "diary_id")
    private Long id;
    private String diary_title;
    private String diary_content;

    private int diary_like_count;
    private int diary_scrap_count;
    private LocalDateTime diary_create_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menber_id")
    private Member member;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<DiaryFile> diaryFiles = new ArrayList<>();

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<DiaryLike> diaryLikes = new ArrayList<>();

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<DiaryScrap> diaryScraps = new ArrayList<>();
    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<DiaryComment> diaryComments = new ArrayList<>();
    @Builder
    public Diary(String diary_title, String diary_content, int diary_like_count, int diary_scrap_count){
        this.diary_title = diary_title;
        this.diary_content = diary_content;
        diary_like_count=0;
        diary_scrap_count=0;
    }

    public void update(String diary_title, String diary_content){
        this.diary_title = diary_title;
        this.diary_content = diary_content;
    }

}
