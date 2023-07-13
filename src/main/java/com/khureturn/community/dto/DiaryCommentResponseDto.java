package com.khureturn.community.dto;

import lombok.*;

public class DiaryCommentResponseDto {

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
