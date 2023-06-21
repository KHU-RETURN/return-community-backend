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
    @Id
    @GeneratedValue
    @Column(name = "exam_id")
    private Long id;
    private String examTitle;
    private String examContent;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int examLikeCount;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int examScrapCount;

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
