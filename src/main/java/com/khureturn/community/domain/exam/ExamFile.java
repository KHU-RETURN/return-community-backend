package com.khureturn.community.domain.exam;

import com.khureturn.community.domain.exam.Exam;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ExamFile {
    @Id
    @GeneratedValue
    @Column(name = "exam_file_id")
    private Long id;

    private String exam_file_name;
    private String exam_file_url;
    private String exam_file_size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="exam_id")
    private Exam exam;

}
