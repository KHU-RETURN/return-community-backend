package com.khureturn.community.dto;

import com.khureturn.community.domain.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class DiaryCommentResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CommentDto{
        private Long commentId;
        private String content;
        private int recommentCount;
        private MemberResponseDto.MemberDto user;
        private LocalDateTime createdDate;


    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateDiaryCommentDto{
        private Long commentId;

    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateDiaryCommentDto{
        private Long commentId;

    }



}
