package com.khureturn.community.dto;
import lombok.*;

public class MemberResponseDto {
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GoogleAccountInfoDto {
        private Long memberId;
        private String profileImgURL;
        private String name;
        private String phoneNumber;
    }


    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberBriefInfoDto{
        private Long memberId;
        private String studentId;
        private String name;
        private String profileImgURL;

    }
}
