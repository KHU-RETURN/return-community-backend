package com.khureturn.community.dto;

import lombok.*;

import java.util.List;

public class DiaryCommentRequestDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateCommentDto{
        private String content;
        private List<String> hashtagList;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateCommentDto{
        private String content;
    }

}
