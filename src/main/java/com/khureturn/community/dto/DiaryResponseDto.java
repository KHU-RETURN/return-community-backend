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
        private MemberResponseDto.MemberDto member;
        private String title;
        private String content;

        private List<String> mediaList;

        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
        private int viewCount;
        private int bookmarkedCount;
        private Boolean isAnonymous;

        private Boolean isMyPost;
        private int likeCount;
        private int commentCount;
    }


    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DiaryListDto {
        private List<DiaryDto> diaryDtoList;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateDiaryDto {
        private Long postId;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateDiaryDto {
        private Long postId;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DiarySortDto{
        private Long diaryId;
        private String title;
        private String thumbnailImgURL;
        private int likeCount;
        private int commentCount;
        private int viewCount;
        private MemberResponseDto.MemberSortDto member;
        private LocalDateTime createdDate;
        private Boolean isAnonymous;
        private Boolean isMyPost;

        private Boolean isLiked;
        private Boolean isBookmarked;
    }








}
