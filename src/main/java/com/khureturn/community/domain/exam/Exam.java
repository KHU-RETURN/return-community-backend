package com.khureturn.community.domain.exam;

import com.khureturn.community.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Exam {
    @Id
    @GeneratedValue
    @Column(name = "exam_id")
    private Long id;
    private String exam_title;
    private String exam_content;

    private LocalDateTime exam_create_date;
    private int like_count;
    private int scrap_count;

    @OneToMany(mappedBy ="exam", cascade = CascadeType.ALL)
    private List<ExamFile> examFiles = new ArrayList<>();
    @OneToMany(mappedBy ="exam", cascade = CascadeType.ALL)
    private List<ExamLike> examLikes = new ArrayList<>();

    @OneToMany(mappedBy ="exam", cascade = CascadeType.ALL)
    private List<ExamScrap> examScraps = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;
}
