package com.khureturn.community.dto;

import lombok.*;

import javax.print.attribute.standard.Media;
import java.time.LocalDateTime;
import java.util.List;


public class DiaryRequestDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateDiaryDto{
        private String title;
        private String content;
        private Boolean isAnonymous;
        private Integer thumbnailIndex;

    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateDiaryDto{
        private String title;
        private String content;
        private Boolean isAnonymous;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DiaryGetDto{
        private int cursor;
        private int size;
    }




}
