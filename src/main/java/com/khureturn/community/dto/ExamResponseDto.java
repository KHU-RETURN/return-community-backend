package com.khureturn.community.dto;

import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

public class ExamResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateExamDto{
        private Long examId;

    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateExamDto{
        private Long examId;

    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ExamDto{
        private Long examId;
        private Boolean isLiked;
        private Boolean isBookmarked;
        private Boolean isMyPost;
        private String title;
        private String content;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
        private int viewCount;
        private int bookmarkedCount;
        private int likedCount;
        private Boolean isAnonymous;
        private List<String> fileList;
        private MemberResponseDto.MemberSortDto member;

    }
}
