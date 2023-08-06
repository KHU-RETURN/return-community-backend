package com.khureturn.community.domain.exam;

import com.khureturn.community.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExamFile extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_file_id")
    private Long id;

    private String examFileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="exam_id")
    private Exam exam;

}
