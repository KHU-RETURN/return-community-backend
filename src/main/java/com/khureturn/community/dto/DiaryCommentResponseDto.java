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
        private List<DiaryCommentResponseDto.ReCommentDto> recomments;
        private MemberResponseDto.MemberSortDto user;
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

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ReCommentDto{
        private Long recommentId;
        private String content;
        private MemberResponseDto.MemberSortDto user;
        private LocalDateTime createdDate;

    }



}
