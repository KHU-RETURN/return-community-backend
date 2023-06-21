package com.khureturn.community.dto;

import lombok.*;

public class MemberResponseDto {
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberDto{
        private Long memberId;
        private String profileImgURL;
        private String name;
        private String phoneNumber;
    }
}
