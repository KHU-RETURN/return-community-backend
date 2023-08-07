package com.khureturn.community.domain.exam;

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
public class Exam extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id")
    private Long id;
    private String examTitle;
    private String examContent;
    private Boolean isAnonymous;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int examLikeCount;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int examScrapCount;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int examViewCount;

    @Builder.Default
    @OneToMany(mappedBy ="exam", cascade = CascadeType.ALL)
    private List<ExamFile> examFiles = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy ="exam", cascade = CascadeType.ALL)
    private List<ExamLike> examLikes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy ="exam", cascade = CascadeType.ALL)
    private List<ExamScrap> examScraps = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;

    public void update(String examTitle, String examContent, Boolean isAnonymous){
        this.examTitle=examTitle;
        this.examContent=examContent;
        this.isAnonymous=isAnonymous;
    }
}
