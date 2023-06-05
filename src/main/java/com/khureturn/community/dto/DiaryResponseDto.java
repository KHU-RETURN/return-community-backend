package com.khureturn.community.dto;

import com.khureturn.community.domain.Member;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

public class DiaryResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DiaryDto{
        private Long diaryId;
        private Boolean isLiked;
        private Boolean isBookmarked;
        private Member member;
        private String title;
        private String content;

        private List<String> mediaList;

        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
        private Long viewCount;
        private Long bookmarkedCount;
        private Boolean isAnonymous;

        private Boolean isMyPost;
        private Long likeCount;
        private Long commentCount;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DiaryListDto {
        private List<DiaryDto> diaryDtoList;
        private Integer size;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateDiaryDto {
        private Long DiaryId;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateDiaryDto {
        private Long DiaryId;
    }






}
