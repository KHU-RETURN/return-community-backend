package com.khureturn.community.dto;

import lombok.*;

public class ExamRequestDto {
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateExamDto{
        private String title;
        private String content;
        private Boolean isAnonymous;

    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateExamDto{
        private String title;
        private String content;
        private Boolean isAnonymous;

    }

}
